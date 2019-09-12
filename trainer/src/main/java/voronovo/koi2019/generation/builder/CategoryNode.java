package voronovo.koi2019.generation.builder;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class CategoryNode {
    private Map<String, CategoryNode> nodes = new HashMap<>();
    private String generator;
    private String name;
    private String title;

    public CategoryNode(String name, String title) {
        this.name = name;
        this.title = title;
    }
}
