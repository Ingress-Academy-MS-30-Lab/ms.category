package az.ingress.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
@FieldDefaults(level = PRIVATE)
@Where(clause = "active = true")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    @OneToMany(mappedBy = "category", cascade = {PERSIST, MERGE}, fetch = LAZY)
    @JsonManagedReference
    Set<CategoryTranslationEntity> translations;
    Boolean active;
    @ManyToOne(fetch = LAZY)
    @JsonBackReference
    CategoryEntity parentCategory;
    @OneToMany(mappedBy = "parentCategory", cascade = {PERSIST, MERGE}, fetch = LAZY)
    @JsonManagedReference
    Set<CategoryEntity> subCategories;
}