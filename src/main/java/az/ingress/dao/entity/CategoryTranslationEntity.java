package az.ingress.dao.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import java.time.LocalDateTime;

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
    String language;
    String name;
    String description;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    CategoryEntity category;

    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;
}