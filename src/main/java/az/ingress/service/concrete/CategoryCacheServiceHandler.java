package az.ingress.service.concrete;

import az.ingress.config.model.RedisCacheKeysProperties;
import az.ingress.logger.ApplicationLogger;
import az.ingress.model.dto.CategoryCacheDto;
import az.ingress.model.response.CategoryResponse;
import az.ingress.service.abstraction.CategoryCacheService;
import az.ingress.util.CacheUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static az.ingress.mapper.CategoryCacheMapper.CATEGORY_CACHE_MAPPER;
import static az.ingress.model.constants.ApplicationConstants.CATEGORY_CACHE_DAYS;
import static az.ingress.model.constants.ApplicationConstants.CUSTOM_THREAD_POOL;

@Service
@RequiredArgsConstructor
public class CategoryCacheServiceHandler implements CategoryCacheService {

    private static final ApplicationLogger log = ApplicationLogger.getLogger(CategoryCacheServiceHandler.class);
    private final RedisCacheKeysProperties redisCacheKeysProperties;
    private final CacheUtil cacheUtil;


    @Override
    @Async(value = CUSTOM_THREAD_POOL)
    public void saveCategoryToCache(CategoryResponse category) {

        String key = redisCacheKeysProperties
                .getCategoryPrefix()
                .formatted(category.getId());

        log.info("ActionLog.saveCategoryToCache.start id: {}, key: {}",
                category.getId(),
                key
        );
        var cacheCategory = CATEGORY_CACHE_MAPPER.toCache(category);

        cacheUtil.saveToCache(key, cacheCategory, CATEGORY_CACHE_DAYS);

        log.info("ActionLog.saveCategoryToCache.end id: {}", category.getId());
        log.info("ActionLog.saveCategoryToCache.end response: saved");
    }


    @Override
    public Optional<CategoryCacheDto> getCategoryFromCache(Long id) {
        String key = String.format(redisCacheKeysProperties.getCategoryPrefix(), id);
        log.info("ActionLog.getCategoryFromCache.start key: {}", key);
        return cacheUtil.getBucket(key);
    }

    @Override
    @Async(value = CUSTOM_THREAD_POOL)
    public void saveCategoriesToCache(List<CategoryResponse> categories) {

        log.info("ActionLog.saveCategoriesToCache.start size: {}, key: {}",
                categories.size(),
                redisCacheKeysProperties.getCategories()
        );

        var cacheList = CATEGORY_CACHE_MAPPER.toCacheList(categories);
        cacheUtil.saveToCache(
                redisCacheKeysProperties.getCategories(),
                cacheList,
                CATEGORY_CACHE_DAYS
        );

        log.info("ActionLog.saveCategoriesToCache.end response: successfully saved");
    }

    @Override
    public Optional<List<CategoryCacheDto>> getCategoriesFromCache() {

        String categoriesKey = redisCacheKeysProperties.getCategories();
        log.info("ActionLog.getCategoriesFromCache.start key: {}", categoriesKey);

        Optional<List<CategoryCacheDto>> cachedCategories = cacheUtil.getBucket(categoriesKey);

        if (cachedCategories.isPresent()) {
            List<CategoryCacheDto> list = cachedCategories.get();
            log.info("ActionLog.getCategoriesFromCache.end response: {}", list);
        } else {
            log.info("ActionLog.getCategoriesFromCache.end response: empty");
        }

        return cachedCategories;
    }


    @Override
    @Async(value = CUSTOM_THREAD_POOL)
    @Retryable(
            value = {RuntimeException.class},
            maxAttempts = 2,
            backoff = @Backoff(delay = 2000)
    )
    public void deleteCategoryFromCache(Long id) {

        String categoryKey = redisCacheKeysProperties.getCategoryPrefix().formatted(id);
        String categoriesKey = redisCacheKeysProperties.getCategories();

        log.info("ActionLog.deleteCategoryFromCache.start id: {}, key: {}", id, categoryKey);

        try {
            cacheUtil.deleteFromCache(categoryKey);
            cacheUtil.deleteFromCache(categoriesKey);

            log.info("ActionLog.deleteCategoryFromCache.end id: {}", id);
            log.info("ActionLog.deleteCategoryFromCache.end response: deleted");

        } catch (RuntimeException ex) {

            log.warn("ActionLog.deleteCategoryFromCache.error id: {}, message: {}, retrying...",
                    id,
                    ex.getMessage()
            );

            throw ex;
        }
    }


}
