package voronovo.koi2019.generation.builder;

import org.springframework.stereotype.Component;
import voronovo.koi2019.generation.calculator.Calculator;
import voronovo.koi2019.generation.calculator.JavaScriptCalculator;
import voronovo.koi2019.generation.condition.*;
import voronovo.koi2019.generation.test.TestBuilder;
import voronovo.koi2019.generation.util.ConstantsHolder;
import voronovo.koi2019.generation.util.RegExpUtil;
import voronovo.koi2019.generation.util.TestBuilderUtil;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static voronovo.koi2019.generation.util.ConstantsHolder.SEPARATOR;

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
            String[] sampleParameters = sample.split(SEPARATOR);
            try {
                TestBuilder builder = new TestBuilder(
                        TestBuilderUtil.getGeneratorSample(sampleParameters[0]),
                        TestBuilderUtil.getPreConditions(sampleParameters[1]),
                        TestBuilderUtil.getPostConditions(sampleParameters[2]),
                        TestBuilderUtil.getAnswerGenerators(sampleParameters[3]),
                        TestBuilderUtil.getCalculator(sampleParameters[4]));
                lastNode.setGenerator(builder);
            } catch (Exception e) {
                throw new IllegalArgumentException("error while creating test builder, path \"" + path + "\"", e);
            }
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
