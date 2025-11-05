package az.ingress.dao.repository;

import az.ingress.dao.entity.CategoryEntity;
import az.ingress.model.enums.Language;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {

    @Query("""
            SELECT DISTINCT c
            FROM CategoryEntity c
            LEFT JOIN FETCH c.translations t
            LEFT JOIN FETCH c.subCategories sc1
            LEFT JOIN FETCH sc1.translations t1
            LEFT JOIN FETCH sc1.subCategories sc2
            LEFT JOIN FETCH sc2.translations t2
            LEFT JOIN FETCH sc2.subCategories sc3
            LEFT JOIN FETCH sc3.translations t3
            LEFT JOIN FETCH sc3.subCategories sc4
            LEFT JOIN FETCH sc4.translations t4
            WHERE c.parentCategory IS NULL
              AND (t.language = :language OR t.language IS NULL)
              AND (t1.language = :language OR t1.language IS NULL)
              AND (t2.language = :language OR t2.language IS NULL)
              AND (t3.language = :language OR t3.language IS NULL)
              AND (t4.language = :language OR t4.language IS NULL)
            """)
    List<CategoryEntity> findAllRootCategories(Language language);

    @Query("""
            SELECT DISTINCT c
            FROM CategoryEntity c
            LEFT JOIN FETCH c.translations t
            LEFT JOIN FETCH c.subCategories sc1
            LEFT JOIN FETCH sc1.translations t1
            LEFT JOIN FETCH sc1.subCategories sc2
            LEFT JOIN FETCH sc2.translations t2
            LEFT JOIN FETCH sc2.subCategories sc3
            LEFT JOIN FETCH sc3.translations t3
            LEFT JOIN FETCH sc3.subCategories sc4
            LEFT JOIN FETCH sc4.translations t4
            WHERE   c.id=:id
              AND (t.language = :language OR t.language IS NULL)
              AND (t1.language = :language OR t1.language IS NULL)
              AND (t2.language = :language OR t2.language IS NULL)
              AND (t3.language = :language OR t3.language IS NULL)
              AND (t4.language = :language OR t4.language IS NULL)
            """)
    Optional<CategoryEntity> findCategory(Language language, Long id);

    @EntityGraph(attributePaths = {"translations"})
    Optional<CategoryEntity> findCategoryById(Long id);
}
