package az.ingress.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    Boolean active;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    CategoryEntity parentCategory;
    @OneToMany(mappedBy = "parentCategory", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference
    List<CategoryEntity> subCategories;
}
