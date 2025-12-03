package az.ingress.config.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "redis.cache.keys")
public class RedisCacheKeysProperties {

    private String categoryPrefix;
    private String categories;
}
