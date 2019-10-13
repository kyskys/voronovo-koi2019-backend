package voronovo.koi2019.condition;

import lombok.Data;
import voronovo.koi2019.generation.type.PreConditionType;

@Data
public class PreCondition {
    private final String target; //переменная, или ответ
    private final PreConditionType preconditionType;
    private final String value;
}