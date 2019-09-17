package voronovo.koi2019.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import voronovo.koi2019.generation.builder.CategoryNode;
import voronovo.koi2019.generation.builder.GeneratorBuilder;
import voronovo.koi2019.generation.task.Task;
import voronovo.koi2019.generation.task.TaskBuilder;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static voronovo.koi2019.rest.TestController.CATEGORIES_URL_PART;

@RestController
@RequestMapping(CATEGORIES_URL_PART)
public class TestController {

    static final String CATEGORIES_URL_PART = "/categories/";
    static final String GENERATE_URL_PART = "generate";

    @Autowired
    private GeneratorBuilder generatorBuilder;

    @GetMapping("**/generate")
    public List<Task> getGenerator(HttpServletRequest request,
                                   @RequestParam(value = "amount", required = false) Integer amount,
                                   @RequestParam(value = "incorrectAnswers", required = false) Integer incorrectAnswers) {
        TaskBuilder taskBuilder = generatorBuilder.findNode(getGeneratorNodePath(request)).getGenerator();
        return taskBuilder.buildBatch(amount, incorrectAnswers);
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
