package az.ingress.service.abstraction;

import az.ingress.model.dto.CategoryCacheDto;
import az.ingress.model.response.CategoryResponse;

import java.util.List;
import java.util.Optional;

public interface CategoryCacheService {
    Optional<CategoryCacheDto> getCategoryFromCache(Long id);

    void saveCategoryToCache(CategoryResponse category);

    Optional<List<CategoryCacheDto>> getCategoriesFromCache();

    void saveCategoriesToCache(List<CategoryResponse> category);

    void deleteCategoryFromCache(Long id);
}
