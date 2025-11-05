package az.ingress.service.concrete;

import az.ingress.dao.entity.CategoryEntity;
import az.ingress.dao.repository.CategoryRepository;
import az.ingress.exception.NotFoundException;
import az.ingress.logger.ApplicationLogger;
import az.ingress.model.dto.UpdateCategoryDto;
import az.ingress.model.enums.Language;
import az.ingress.model.request.CategoryRequest;
import az.ingress.model.response.CategoryResponse;
import az.ingress.service.abstraction.CategoryCacheService;
import az.ingress.service.abstraction.CategoryService;
import az.ingress.util.CategoryTranslationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

import static az.ingress.exception.ErrorMessage.CATEGORY_NOT_FOUND;
import static az.ingress.mapper.CategoryMapper.CATEGORY_MAPPER;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private static final ApplicationLogger log = ApplicationLogger.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;
    private final CategoryTranslationUtil categoryTranslationUtil;
    private final CategoryCacheService categoryCacheService;

    @Override
    public void addCategory(CategoryRequest categoryRequest) {
        log.info("Add category request received: {}", categoryRequest);

        CategoryEntity parentCategory = null;
        if (categoryRequest.getParentCategoryId() != null) {
            log.debug("Fetching parent category with ID: {}", categoryRequest.getParentCategoryId());
            parentCategory = categoryRepository.findById(categoryRequest.getParentCategoryId())
                    .orElseThrow(() -> {
                        log.warn("Parent category not found with ID: {}", categoryRequest.getParentCategoryId());
                        return new NotFoundException(CATEGORY_NOT_FOUND);
                    });
        }

        CategoryEntity savedEntity = categoryRepository.save(CATEGORY_MAPPER.toEntity(categoryRequest, parentCategory));
        log.info("Category successfully created with ID: {}", savedEntity.getId());
    }

    @Override
    public List<CategoryResponse> getCategories(Language language) {
        log.info("Fetching all categories for language: {}", language);

        List<CategoryResponse> categories = categoryCacheService.getCategoriesFromCache();
        if (categories != null && !categories.isEmpty()) {
            log.debug("Categories fetched from cache, count: {}", categories.size());
            return categories;
        }

        log.debug("Cache empty. Fetching categories from DB...");
        List<CategoryEntity> allRootCategories = categoryRepository.findAllRootCategories(language);
        categories = CATEGORY_MAPPER.toResponse(allRootCategories);

        categoryCacheService.saveCategoriesToCache(categories);
        log.info("Categories cached successfully. Count: {}", categories.size());

        return categories;
    }

    @Override
    public void deleteCategory(Long id) {
        log.info("Deleting category with ID: {}", id);

        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Attempt to delete non-existent or inactive category with ID: {}", id);
                    return new NotFoundException(CATEGORY_NOT_FOUND);
                });

        category.setActive(false);
        categoryRepository.save(category);
        categoryCacheService.deleteCategoryFromCache(category.getId());

        log.info("Category with ID: {} successfully deactivated and removed from cache", id);
    }

    @Override
    public void updateCategory(Long categoryId, UpdateCategoryDto dto) {
        log.info("Updating category with ID: {} and DTO: {}", categoryId, dto);

        CategoryEntity category = categoryRepository.findCategoryById(categoryId)
                .orElseThrow(() -> {
                    log.warn("Category not found for update with ID: {}", categoryId);
                    return new NotFoundException(CATEGORY_NOT_FOUND);
                });

        categoryTranslationUtil.updateTranslations(category, new HashSet<>(dto.getNames()));
        categoryRepository.save(category);
        categoryCacheService.deleteCategoryFromCache(category.getId());

        log.info("Category with ID: {} successfully updated and cache invalidated", categoryId);
    }

    @Override
    public CategoryResponse getCategory(Language language, Long id) {
        log.info("Fetching category by ID: {} for language: {}", id, language);

        CategoryResponse categoryFromCache = categoryCacheService.getCategoryFromCache(id);
        if (categoryFromCache != null) {
            log.debug("Category with ID: {} fetched from cache", id);
            return categoryFromCache;
        }

        log.debug("Cache miss. Fetching category from DB...");
        CategoryEntity category = categoryRepository.findCategory(language, id)
                .orElseThrow(() -> {
                    log.warn("Category not found with ID: {}", id);
                    return new NotFoundException(CATEGORY_NOT_FOUND);
                });

        categoryFromCache = CATEGORY_MAPPER.mapToResponseRecursive(category);
        categoryCacheService.saveCategoryToCache(categoryFromCache);

        log.info("Category with ID: {} fetched from DB and cached", id);
        return categoryFromCache;
    }
}
