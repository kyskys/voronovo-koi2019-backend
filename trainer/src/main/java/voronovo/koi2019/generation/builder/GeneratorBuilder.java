package voronovo.koi2019.generation.builder;

import org.springframework.stereotype.Component;
import voronovo.koi2019.generation.api.AnswerGenerator;
import voronovo.koi2019.generation.calculator.JavaScriptCalculator;
import voronovo.koi2019.generation.test.TestBuilder;
import voronovo.koi2019.generation.util.ConstantsHolder;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class GeneratorBuilder {

    private final GeneratorNameConfig generatorNames;
    private final GeneratorSampleConfig generatorSamples;
    private final GeneratorAnswerConfig generatorAnswers;
    private CategoryNode rootNode;

    public GeneratorBuilder(GeneratorNameConfig generatorNames,
                            GeneratorSampleConfig generatorSamples,
                            GeneratorAnswerConfig generatorAnswers) {
        this.generatorNames = generatorNames;
        this.generatorSamples = generatorSamples;
        this.generatorAnswers = generatorAnswers;
    }

    @PostConstruct
    private void postConstruct() {
        rootNode = new CategoryNode();
        generatorSamples.getSamples().forEach((path, sample) -> {
            CategoryNode lastNode = findNode(path, rootNode);
            TestBuilder builder = new TestBuilder(sample, new JavaScriptCalculator(), new AnswerGenerator() {});
            lastNode.setGenerator(builder);
        });
    }

    private CategoryNode findNode(String path, CategoryNode head) {
        return Stream.of(path.split("\\."))
                .reduce(head, (node, name) ->
                        node.getNodes().computeIfAbsent(name, (newName) ->
                                new CategoryNode(newName, generatorNames.getNames().get(newName))
                        ), (node, node2) -> node2);
    }

    private List<AnswerGenerator> getAnswerGenerators(String answerParameters) {
        return Stream
                .of(answerParameters.split(ConstantsHolder.ADDITIONAL_SEPARATOR))
                .map(String::trim)
                .sorted((o1, o2) -> ThreadLocalRandom.current().nextInt(-1, 2))
                .
                .limit(2)
                .
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
