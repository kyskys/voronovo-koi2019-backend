package voronovo.koi2019.generation.condition;

import lombok.Data;

@Data
public class PostCondition {
    private final String target; //variable, or result
    private final PostConditionType preconditionType;
    private final String value;
}
