package az.ingress.mapper;

import az.ingress.dao.entity.CategoryEntity;
import az.ingress.dao.entity.CategoryTranslationEntity;
import az.ingress.model.dto.CategoryNameDto;
import az.ingress.model.request.CategoryRequest;
import az.ingress.model.response.CategoryResponse;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum CategoryMapper {
    CATEGORY_MAPPER;

    public CategoryEntity toEntity(CategoryRequest categoryRequest, CategoryEntity parentCategory) {
        CategoryEntity entity = CategoryEntity.builder().
                active(true).parentCategory(parentCategory).build();
        entity.setTranslations(buildCategoryTranslationEntity(categoryRequest.getName(), entity));
        return entity;
    }

    private Set<CategoryTranslationEntity> buildCategoryTranslationEntity(List<CategoryNameDto> categoryNames,
                                                                          CategoryEntity categoryEntity) {
        return categoryNames.
                stream().
                map(i -> CategoryTranslationEntity.
                        builder().category(categoryEntity).
                        language(i.getLanguage()).
                        name(i.getName()).
                        build()).
                collect(Collectors.toSet());
    }

    public List<CategoryResponse> toResponse(List<CategoryEntity> categoryEntities) {
        return categoryEntities.stream()
                .map(this::mapToResponseRecursive)
                .toList();
    }

    public CategoryResponse mapToResponseRecursive(CategoryEntity entity) {
        return CategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getTranslations().stream()
                        .findFirst()
                        .map(CategoryTranslationEntity::getName)
                        .orElse(null))
                .subCategories(entity.getSubCategories() != null
                        ? entity.getSubCategories().stream()
                        .map(this::mapToResponseRecursive)
                        .toList()
                        : List.of())
                .build();
    }

}