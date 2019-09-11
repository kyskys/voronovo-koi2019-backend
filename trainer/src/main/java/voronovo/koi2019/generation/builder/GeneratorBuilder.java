package voronovo.koi2019.generation.builder;

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
    private CategoryNode rootNode;

    @PostConstruct
    private void postConstruct() {
        rootNode = new CategoryNode();
        generatorSamples.getSamples().forEach((path, sample) -> {
            CategoryNode lastNode = findNode(path, rootNode);
            lastNode.setGenerator(sample);
        });
    }

    private CategoryNode findNode(String path, CategoryNode head) {
        return Stream.of(path.split("\\."))
                .reduce(head, (node, key) ->
                        node.getNodes().computeIfAbsent(key, CategoryNode::new), (node, node2) -> node2);
    }
}
