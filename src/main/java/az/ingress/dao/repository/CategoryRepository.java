package az.ingress.dao.repository;

import az.ingress.dao.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
    List<CategoryEntity> findAllByActiveIsTrue();
}
