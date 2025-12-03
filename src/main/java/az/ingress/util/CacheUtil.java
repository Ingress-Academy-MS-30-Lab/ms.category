package az.ingress.util;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

import static az.ingress.model.constants.ApplicationConstants.CUSTOM_THREAD_POOL;

@Component
@RequiredArgsConstructor
public class CacheUtil {

    private final RedissonClient redissonClient;

    public <T> Optional<T> getBucket(String cacheKey) {
        try {
            RBucket<T> bucket = redissonClient.getBucket(cacheKey);

            if (bucket == null) {
                return Optional.empty();
            }

            T value = bucket.get();
            return Optional.ofNullable(value);

        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Async(CUSTOM_THREAD_POOL)
    public <T> void saveToCache(String cacheKey,
                                T value,
                                Duration duration) {
        RBucket<T> bucket = redissonClient.getBucket(cacheKey);
        bucket.set(value);
        bucket.expire(duration);
    }
    @Async(CUSTOM_THREAD_POOL)
    public void deleteFromCache(String cacheKey) {
        RBucket<?> bucket = redissonClient.getBucket(cacheKey);
        if (bucket != null && bucket.isExists()) {
            bucket.delete();
        }
    }

}
