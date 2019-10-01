package voronovo.koi2019.generation.condition;

import lombok.Data;

@Data
public class PreCondition {
    private final String target; //variable, or result
    private final PreConditionType preconditionType;
    private final String value;
}