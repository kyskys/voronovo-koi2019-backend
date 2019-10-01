package voronovo.koi2019.generation.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static voronovo.koi2019.generation.util.ConstantsHolder.CONDITIONS_SEPARATOR;

public class ConditionsParser {
    public static List<PreCondition> parsePre(String conditions) {
        if (conditions != null) {
            return Arrays.stream(conditions.split(CONDITIONS_SEPARATOR)).map(strCondition -> {
                String[] conditionParts = strCondition.trim().split(" ");
                return new PreCondition(conditionParts[0], PreConditionType.byIdentifier(conditionParts[1]), conditionParts[2]);
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static List<PostCondition> parsePost(String conditions) {
        if (conditions != null) {
            return Arrays.stream(conditions.split(CONDITIONS_SEPARATOR)).map(strCondition -> {
                String[] conditionParts = strCondition.trim().split(" ");
                return new PostCondition(conditionParts[0], PreConditionType.byIdentifier(conditionParts[1]), conditionParts[2]);
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
