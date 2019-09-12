package voronovo.koi2019.generation.builder;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Stream;

@Component
public class GeneratorBuilder {

    @Autowired
    private GeneratorNameConfig generatorNames;
    @Autowired
    private GeneratorSampleConfig generatorSamples;
    @Getter
    private CategoryNode rootNode;

    @PostConstruct
    private void postConstruct() {
        rootNode = new CategoryNode();
        generatorSamples.getSamples().forEach((path, sample) -> {
            CategoryNode lastNode = findNode(path, rootNode, "\\.");
            lastNode.setGenerator(sample);
        });
    }

    private CategoryNode findNode(String path, CategoryNode head, String regex) {
        return Stream.of(path.split(regex))
                .reduce(head, (node, name) ->
                        node.getNodes().computeIfAbsent(name, (newName) ->
                                new CategoryNode(newName, generatorNames.getNames().get(newName))
                        ), (node, node2) -> node2);
    }

    public CategoryNode findNode(String path) {
        return findNode(path, rootNode, "/");
    }
}
