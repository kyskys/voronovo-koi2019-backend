package voronovo.koi2019.generation.builder;

import org.springframework.stereotype.Component;
import voronovo.koi2019.config.GeneratorNameConfig;
import voronovo.koi2019.config.GeneratorSampleConfig;
import voronovo.koi2019.generation.test.MultiTestBuilder;
import voronovo.koi2019.generation.util.TestBuilderUtil;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class GeneratorBuilder {

    private final GeneratorNameConfig generatorNames;
    private final GeneratorSampleConfig generatorSamples;
    private CategoryNode rootNode;

    public GeneratorBuilder(GeneratorNameConfig generatorNames,
                            GeneratorSampleConfig generatorSamples) {
        this.generatorNames = generatorNames;
        this.generatorSamples = generatorSamples;
    }

    @PostConstruct
    private void postConstruct() {
        rootNode = new CategoryNode();
        generatorSamples.getSamples().forEach((path, sample) -> {
            CategoryNode lastNode = findNode(path, rootNode);
            MultiTestBuilder multiBuilder = TestBuilderUtil.getMultiBuilder(sample);
            lastNode.setGenerator(multiBuilder);
        });
    }

    private CategoryNode findNode(String path, CategoryNode head) {
        return Stream.of(path.split("\\."))
                .reduce(head, (node, name) ->
                        node.getNodes().computeIfAbsent(name, (newName) ->
                                new CategoryNode(newName, generatorNames.getNames().get(newName))
                        ), (node, node2) -> node2);
    }

    public CategoryNode findNode(String path) {
        return path.isEmpty() ? rootNode :
                Stream.of(path.split("/"))
                        .reduce(rootNode, (node, name) ->
                                node.getNodes().getOrDefault(name, new CategoryNode()), (node, node2) -> node2);
    }

    public Map<String, String> getNodeNames(CategoryNode node) {
        return node.getNodes()
                .keySet()
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        key -> generatorNames.getNames().get(key)));
    }
}
