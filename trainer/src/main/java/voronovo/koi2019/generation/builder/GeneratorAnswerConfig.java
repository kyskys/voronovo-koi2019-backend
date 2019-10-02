package voronovo.koi2019.generation.builder;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Configuration
@PropertySource(value = "classpath:answer.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "sample")
@Data
public class GeneratorAnswerConfig {
    private Map<String, String> answers;
}
