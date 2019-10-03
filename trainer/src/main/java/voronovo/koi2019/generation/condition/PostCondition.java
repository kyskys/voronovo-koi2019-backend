package voronovo.koi2019.generation.condition;

import lombok.Data;

@Data
public class PostCondition {
    private final PostConditionType type;
    private final String value;
}
