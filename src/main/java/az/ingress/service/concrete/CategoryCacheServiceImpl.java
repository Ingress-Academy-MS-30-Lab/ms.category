package az.ingress.service.concrete;

import az.ingress.model.response.CategoryResponse;
import az.ingress.service.abstraction.CategoryCacheService;
import az.ingress.util.CacheUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryCacheServiceImpl implements CategoryCacheService {

    private static final Logger log = LoggerFactory.getLogger(CategoryCacheServiceImpl.class);

    private final CacheUtil cacheUtil;

    @Value("${redis.cache.keys.category-prefix}")
    private String categoryCachePrefix;

    @Value("${redis.cache.keys.categories}")
    private String categoriesCacheKey;

    @Override
    @Async(value = "asyncExecutor")
    public void saveCategoryToCache(CategoryResponse category) {
        String key = categoryCachePrefix + category.getId();
        log.info("Saving category with id={} to cache using key={}", category.getId(), key);
        cacheUtil.saveToCache(key, category, 1L, ChronoUnit.DAYS);
        log.info("Category with id={} saved to cache successfully", category.getId());
    }

    @Override
    public CategoryResponse getCategoryFromCache(Long id) {
        String key = categoryCachePrefix + id;
        log.info("Fetching category from cache using key={}", key);
        CategoryResponse cachedCategory = cacheUtil.getBucket(key);
        if (cachedCategory != null) {
            log.info("Category with id={} found in cache", id);
        } else {
            log.info("Category with id={} not found in cache", id);
        }
        return cachedCategory;
    }

    @Override
    @Async(value = "asyncExecutor")
    public void saveCategoriesToCache(List<CategoryResponse> categories) {
        log.info("Saving {} categories to cache with key={}", categories.size(), categoriesCacheKey);
        cacheUtil.saveToCache(categoriesCacheKey, categories, 1L, ChronoUnit.DAYS);
        log.info("Categories saved to cache successfully");
    }

    @Override
    public List<CategoryResponse> getCategoriesFromCache() {
        log.info("Fetching all categories from cache using key={}", categoriesCacheKey);
        List<CategoryResponse> cachedCategories = cacheUtil.getBucket(categoriesCacheKey);
        if (cachedCategories != null && !cachedCategories.isEmpty()) {
            log.info("Fetched {} categories from cache", cachedCategories.size());
        } else {
            log.info("No categories found in cache");
        }
        return cachedCategories;
    }


    @Override
    @Async(value = "asyncExecutor")
    @Retryable(value = {RuntimeException.class},
            maxAttempts = 2,
            backoff = @Backoff(delay = 2000))
    public void deleteCategoryFromCache(Long id) {
        String key = categoryCachePrefix + id;
        log.info("Attempting to delete category with id={} from cache", id);
        try {
            cacheUtil.deleteFromCache(key);
            cacheUtil.deleteFromCache(categoriesCacheKey);
            log.info("Category with id={} and categories list cache deleted", id);
        } catch (RuntimeException ex) {
            log.warn("Failed to delete category with id={} from cache. Retrying...", id, ex);
            throw ex;
        }
    }

}
