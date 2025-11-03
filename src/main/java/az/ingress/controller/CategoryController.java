package az.ingress.controller;

import az.ingress.dao.entity.CategoryEntity;
import az.ingress.model.request.CategoryRequest;
import az.ingress.service.abstraction.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public void addCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryService.addCategory(categoryRequest);
    }
    @GetMapping
    public List<CategoryEntity> getCategories(){
        return categoryService.getCategories();
    }
}
