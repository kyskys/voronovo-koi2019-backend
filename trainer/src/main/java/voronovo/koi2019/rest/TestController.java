package voronovo.koi2019.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;
import voronovo.koi2019.generation.builder.CategoryNode;
import voronovo.koi2019.generation.builder.GeneratorBuilder;

import javax.servlet.http.HttpServletRequest;

import static voronovo.koi2019.rest.TestController.CATEGORIES_URL_PART;

@RestController
@RequestMapping(CATEGORIES_URL_PART)
public class TestController {

    static final String CATEGORIES_URL_PART = "/categories/";

    @Autowired
    private GeneratorBuilder generatorBuilder;

    @GetMapping("**/generate")
    public String getGenerator(HttpServletRequest request) {
        return generatorBuilder.findNode(getNodePath(request)).getGenerator();
    }

    @GetMapping("**")
    public CategoryNode findNodes(HttpServletRequest request) {
        return generatorBuilder.findNode(getNodePath(request));
    }

    private String getNodePath(HttpServletRequest request) {
        String nodePath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        return nodePath.replace(CATEGORIES_URL_PART, "");
    }
}
