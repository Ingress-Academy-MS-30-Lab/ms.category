package az.ingress.controller;

import az.ingress.model.dto.UpdateCategoryDto;
import az.ingress.model.enums.Language;
import az.ingress.model.request.CategoryRequest;
import az.ingress.model.response.CategoryResponse;
import az.ingress.service.abstraction.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void addCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryService.addCategory(categoryRequest);
    }

    @GetMapping
    public List<CategoryResponse> getCategories(@RequestHeader(value = "Accept-Language", defaultValue = "AZ") Language language) {
        return categoryService.getCategories(language);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryDto categoryDto) {
        categoryService.updateCategory(id, categoryDto);
    }

    @GetMapping("{id}")
    public CategoryResponse getCategory(
            @RequestHeader(value = "Accept-Language", defaultValue = "AZ") Language language,
            @PathVariable Long id) {
        return categoryService.getCategory(language, id);
    }
}
