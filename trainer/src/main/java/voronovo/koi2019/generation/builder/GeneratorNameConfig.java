package voronovo.koi2019.generation.builder;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Configuration
@PropertySource(value = "classpath:name.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "generator")
@Data
public class GeneratorNameConfig {
    private Map<String, String> names;
}
