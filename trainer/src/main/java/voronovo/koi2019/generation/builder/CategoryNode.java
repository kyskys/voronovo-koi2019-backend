package voronovo.koi2019.generation.builder;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@NoArgsConstructor
public class CategoryNode {
    private Map<String, CategoryNode> nodes = new HashMap<>();
    private String generator;
    private String name;

    public CategoryNode(String name) {
        this.name = name;
    }

    public CategoryNode findNode(String nodeName) {
        return nodes.putIfAbsent(nodeName, new CategoryNode());
    }
}
