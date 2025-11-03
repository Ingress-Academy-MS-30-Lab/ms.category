package az.ingress.service.abstraction;

import az.ingress.dao.entity.CategoryEntity;
import az.ingress.model.request.CategoryRequest;

import java.util.List;

public interface CategoryService {
    void addCategory(CategoryRequest categoryRequest);

    List<CategoryEntity> getCategories();

}
