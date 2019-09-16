package voronovo.koi2019.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;
import voronovo.koi2019.generation.api.AnswerGenerator;
import voronovo.koi2019.generation.api.JavaScriptCalculator;
import voronovo.koi2019.generation.api.SimpleIntegerResultCalculator;
import voronovo.koi2019.generation.builder.CategoryNode;
import voronovo.koi2019.generation.builder.GeneratorBuilder;
import voronovo.koi2019.generation.task.Task;
import voronovo.koi2019.generation.task.TaskBuilder;

import javax.servlet.http.HttpServletRequest;

import static voronovo.koi2019.rest.TestController.CATEGORIES_URL_PART;

@RestController
@RequestMapping(CATEGORIES_URL_PART)
public class TestController {

    static final String CATEGORIES_URL_PART = "/categories/";
    static final String GENERATE_URL_PART = "generate";

    @Autowired
    private GeneratorBuilder generatorBuilder;

    @GetMapping("**/generate")
    public Task getGenerator(HttpServletRequest request) {
        String generator = generatorBuilder.findNode(getGeneratorNodePath(request)).getGenerator();
        TaskBuilder taskBuilder = new TaskBuilder(generator, new JavaScriptCalculator(), new AnswerGenerator() {});
        return taskBuilder.build(3);
    }

    @GetMapping("**")
    public CategoryNode findNodes(HttpServletRequest request) {
        return generatorBuilder.findNode(getNodePath(request));
    }

    private String getGeneratorNodePath(HttpServletRequest request) {
        String nodePath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        return nodePath.replace(CATEGORIES_URL_PART, "").replace(GENERATE_URL_PART, "");
    }

    private String getNodePath(HttpServletRequest request) {
        String nodePath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        return nodePath.replace(CATEGORIES_URL_PART, "");
    }
}
