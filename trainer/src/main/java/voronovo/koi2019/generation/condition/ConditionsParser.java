package voronovo.koi2019.generation.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static voronovo.koi2019.generation.util.ConstantsHolder.ADDITIONAL_SEPARATOR;

public class ConditionsParser {
    public static List<PreCondition> parsePre(String conditions) {
        if (conditions != null) {
            return Arrays.stream(conditions.split(ADDITIONAL_SEPARATOR)).map(PreConditionType::find).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static List<PostCondition> parsePost(String conditions) {
        if (conditions != null) {
            return Arrays.stream(conditions.split(ADDITIONAL_SEPARATOR)).map(PostConditionType::find).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
