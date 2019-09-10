package voronovo.koi2019;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@SpringBootApplication
@EnableSwagger2WebMvc
@Import({SpringDataRestConfiguration.class})
public class MainApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    @Primary
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        return builder.modulesToInstall(new JavaTimeModule());
    }

//    public class VariableInitListener {
//        private voronovo.koi2019.generation.task.TaskBuilder taskContext;
//        private String variableId;
//        private voronovo.koi2019.generation.condition.Precondition precondition;
//
//        VariableInitListener create(String variableId, voronovo.koi2019.generation.condition.Precondition precondition, voronovo.koi2019.generation.task.TaskBuilder taskContext) {
//            return new VariableInitListener(variableId, precondition, taskContext);
//        }
//
//        private VariableInitListener(String variableId, voronovo.koi2019.generation.condition.Precondition precondition, voronovo.koi2019.generation.task.TaskBuilder taskContext) {
//            this.taskContext = taskContext;
//            this.precondition = precondition;
//            this.variableId = variableId;
//        }
//
//        public Optional<VariableInitListener> tryToCalculate() {
//            return null;
////            precondition.getPreconditionType().generateValue();
//        }
//    }

//    public static void main(String[] args) {
////        voronovo.koi2019.generation.util.RegExpUtil.findAll("dsnfkjsdnf kjnsd var skdf [var1] [var1]  [var222]", voronovo.koi2019.generation.util.RegexConst.VARIABLE_REGEX).forEach(System.out::println);
////        voronovo.koi2019.generation.util.RegExpUtil.findAllUnique("dsnfkjsdnf kjnsd var skdf [var1] [var1]  [var222]", voronovo.koi2019.generation.util.RegexConst.VARIABLE_REGEX).forEach(System.out::println);
//        TaskBuilder taskBuilder = new TaskBuilder("[var1] + [var2] * [var3] + [var444]", "var1 between 1;100 " +
//                "<> var2 between 50;55 <> var3 between 3;33 <> var444 == 9", new SimpleIntegerResultCalculator(), new AnswerGenerator() {});
//        //3 варианта неправильных ответов
//        Task build = taskBuilder.build(3);
//        System.out.println(build.toString());
//    }
}
