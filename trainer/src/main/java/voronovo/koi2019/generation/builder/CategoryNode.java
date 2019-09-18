package voronovo.koi2019.generation.builder;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import voronovo.koi2019.generation.test.TestBuilder;
import voronovo.koi2019.generation.util.NodeUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
@NoArgsConstructor
public class CategoryNode {
    private Map<String, CategoryNode> nodes = new HashMap<>();
    @JsonIgnore
    private TestBuilder generator;
    @JsonIgnore
    private String name;
    private String title;

    public CategoryNode(String name, String title) {
        this.name = name;
        this.title = title;
    }

    @JsonGetter("nodes")
    public Map<String, String> getNodeNames() {
        return NodeUtil.getNodeNames(this);
    }

    @JsonProperty("isGenerator")
    public boolean isGenerator() {
        return Objects.isNull(getGenerator());
    }
}
