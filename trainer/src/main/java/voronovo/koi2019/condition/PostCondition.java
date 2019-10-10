package voronovo.koi2019.condition;

import lombok.Data;
import voronovo.koi2019.generation.type.PostConditionType;

@Data
public class PostCondition {
    private final PostConditionType type;
    private final String value;
}
