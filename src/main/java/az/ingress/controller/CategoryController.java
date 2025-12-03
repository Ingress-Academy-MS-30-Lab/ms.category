package az.ingress.controller;

import az.ingress.model.dto.UpdateCategoryDto;
import az.ingress.model.request.CategoryRequest;
import az.ingress.model.response.CategoryResponse;
import az.ingress.service.abstraction.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void addCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryService.addCategory(categoryRequest);
    }

    @GetMapping
    public List<CategoryResponse> getCategories() {
        return categoryService.getCategories();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateCategory(@PathVariable Long id,@Valid @RequestBody UpdateCategoryDto categoryDto) {
        categoryService.updateCategory(id, categoryDto);
    }

    @GetMapping("{id}")
    public CategoryResponse getCategory(
            @PathVariable Long id) {
        return categoryService.getCategory(id);
    }
}
