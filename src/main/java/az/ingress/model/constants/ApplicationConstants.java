package az.ingress.model.constants;

import java.time.Duration;

public final class ApplicationConstants {
    public static final String CUSTOM_THREAD_POOL = "asyncExecutor";
    public static final String CACHE_NAME_PREFIX = "CacheAsync-";
    public static final int AWAIT_TERMINATION_SECOND = 30;


    public static Duration CATEGORY_CACHE_DAYS = Duration.ofDays(1L);
}
