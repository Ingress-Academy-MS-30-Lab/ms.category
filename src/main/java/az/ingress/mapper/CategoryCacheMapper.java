package az.ingress.mapper;

import az.ingress.model.dto.CategoryCacheDto;
import az.ingress.model.response.CategoryResponse;

import java.util.List;
import java.util.stream.Collectors;

public enum CategoryCacheMapper {
    CATEGORY_CACHE_MAPPER;

    public  CategoryCacheDto toCache(CategoryResponse response) {
        CategoryCacheDto dto = new CategoryCacheDto();
        dto.setId(response.getId());
        dto.setName(response.getName());

        if (response.getSubCategories() != null && !response.getSubCategories().isEmpty()) {
            List<CategoryCacheDto> subDtos = response.getSubCategories()
                    .stream()
                    .map(CATEGORY_CACHE_MAPPER::toCache)
                    .collect(Collectors.toList());

            dto.setSubCategories(subDtos);
        } else {
            dto.setSubCategories(null);
        }

        return dto;
    }

    public List<CategoryCacheDto> toCacheList(List<CategoryResponse> responses) {
        return responses.stream()
                .map(CATEGORY_CACHE_MAPPER::toCache)
                .collect(Collectors.toList());
    }

}