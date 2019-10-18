package voronovo.koi2019.generation.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import voronovo.koi2019.generation.builder.CategoryNode;
import voronovo.koi2019.config.GeneratorNameConfig;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NodeUtil {
    private static Map<String, String> generatorNames;

    @Autowired
    private void setGeneratorNames(GeneratorNameConfig config) {
        NodeUtil.generatorNames = config.getNames();
    }

    public static Map<String, String> getNodeNames(CategoryNode node) {
        return node.getNodes()
                .keySet()
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        key -> generatorNames.get(key)));
    }
}
