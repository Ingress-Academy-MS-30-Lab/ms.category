package az.ingress.dao.entity;

import az.ingress.model.enums.Language;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "category_translations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryTranslationEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    @Enumerated(value = STRING)
    Language language;
    String name;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    CategoryEntity category;
}