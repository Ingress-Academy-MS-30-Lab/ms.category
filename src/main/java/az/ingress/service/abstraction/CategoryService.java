package az.ingress.service.abstraction;

import az.ingress.model.dto.UpdateCategoryDto;
import az.ingress.model.enums.Language;
import az.ingress.model.request.CategoryRequest;
import az.ingress.model.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    void addCategory(CategoryRequest categoryRequest);

    List<CategoryResponse> getCategories(Language language);

    void deleteCategory(Long id);

    void updateCategory(Long id, UpdateCategoryDto categoryName);

    CategoryResponse getCategory(Language language, Long id);
}
