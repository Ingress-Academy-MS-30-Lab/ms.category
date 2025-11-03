package az.ingress.mapper;

import az.ingress.dao.entity.CategoryEntity;
import az.ingress.model.request.CategoryRequest;

import java.util.List;

public enum CategoryMapper {
    CATEGORY_MAPPER;

    public CategoryEntity toEntity(CategoryRequest categoryRequest) {
        CategoryEntity entity = CategoryEntity.builder().
                name(categoryRequest.getName()).
                active(true).build();
        entity.setSubCategories(buildChildCategory(categoryRequest, entity));
        return entity;
    }

    private List<CategoryEntity> buildChildCategory(CategoryRequest categoryRequest,
                                                    CategoryEntity categoryEntity) {
        return categoryRequest.getChildCategories().
                stream().
                map(i -> CategoryEntity.
                        builder().
                        name(i.getName()).
                        active(true).
                        parentCategory(categoryEntity).
                        build()).toList();
    }
}
