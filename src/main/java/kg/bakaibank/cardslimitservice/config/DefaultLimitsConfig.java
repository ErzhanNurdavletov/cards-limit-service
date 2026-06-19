package kg.bakaibank.cardslimitservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.default-limits")
@Getter
@Setter
public class DefaultLimitsConfig {
    private String withdrawalLimit;
    private String transferLimit;
}