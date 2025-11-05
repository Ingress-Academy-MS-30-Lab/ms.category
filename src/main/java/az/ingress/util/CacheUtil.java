package az.ingress.util;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

@Component
@RequiredArgsConstructor
public class CacheUtil {

    private final RedissonClient redissonClient;

    public <T> T getBucket(String cacheKey) {
        RBucket<T> bucket = redissonClient.getBucket(cacheKey);
        return bucket != null ? bucket.get() : null;
    }

    public <T> void saveToCache(String cacheKey,
                                T value,
                                Long expireTime,
                                TemporalUnit timeUnit) {
        RBucket<T> bucket = redissonClient.getBucket(cacheKey);
        bucket.set(value);
        bucket.expire(Duration.of(expireTime, timeUnit));
    }

    public void deleteFromCache(String cacheKey) {
        RBucket<?> bucket = redissonClient.getBucket(cacheKey);
        if (bucket != null && bucket.isExists()) {
            bucket.delete();
        }
    }

}
