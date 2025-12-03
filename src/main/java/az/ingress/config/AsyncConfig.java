package az.ingress.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import static az.ingress.model.constants.ApplicationConstants.*;

@Configuration
public class AsyncConfig {

    @Bean(name = CUSTOM_THREAD_POOL)
    public Executor asyncExecutor() {
        var availableProcessors = Runtime.getRuntime().availableProcessors();

        var corePoolSize = Math.max(2, availableProcessors);
        var maxPoolSize = corePoolSize * 2;
        var queueCapacity = maxPoolSize * 50;

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(CACHE_NAME_PREFIX);

        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(AWAIT_TERMINATION_SECOND);
        executor.initialize();
        return executor;
    }
}
