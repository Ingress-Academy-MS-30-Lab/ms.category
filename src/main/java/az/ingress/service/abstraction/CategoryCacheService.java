package az.ingress.service.abstraction;

import az.ingress.model.response.CategoryResponse;

import java.util.List;

public interface CategoryCacheService {
    CategoryResponse getCategoryFromCache(Long id);

    void saveCategoryToCache(CategoryResponse category);

    List<CategoryResponse> getCategoriesFromCache();

    void saveCategoriesToCache(List<CategoryResponse> category);

    void deleteCategoryFromCache(Long id);
}
