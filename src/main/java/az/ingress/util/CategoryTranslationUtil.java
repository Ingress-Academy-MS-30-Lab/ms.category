package az.ingress.util;

import az.ingress.dao.entity.CategoryEntity;
import az.ingress.dao.entity.CategoryTranslationEntity;
import az.ingress.model.dto.CategoryNameDto;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryTranslationUtil {

    public void updateTranslations(CategoryEntity category, Set<CategoryNameDto> newNames) {
        Map<String, CategoryTranslationEntity> existing = category.getTranslations()
                .stream()
                .collect(Collectors.toMap(CategoryTranslationEntity::getLanguage, t -> t));

        for (CategoryNameDto nameDto : newNames) {
            String lang = nameDto.getLanguage();
            String newName = nameDto.getName();

            if (existing.containsKey(lang)) {
                existing.get(lang).setName(newName);
            } else {
                CategoryTranslationEntity newTranslation = CategoryTranslationEntity.builder()
                        .language(lang)
                        .name(newName)
                        .category(category)
                        .build();
                category.getTranslations().add(newTranslation);
            }
        }
    }
}
