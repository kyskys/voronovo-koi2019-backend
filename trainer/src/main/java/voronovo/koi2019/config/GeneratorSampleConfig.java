package voronovo.koi2019.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Configuration
@PropertySource(value = "classpath:generator.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "builder")
@Data
public class GeneratorSampleConfig {
    private Map<String, String> samples;
}
