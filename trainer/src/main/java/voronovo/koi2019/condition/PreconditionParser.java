package voronovo.koi2019.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PreconditionParser {
    public static List<Precondition> parse(String conditions) {
        if (conditions != null) {
            return Arrays.stream(conditions.split("<>")).map(strCondition -> {
                String[] conditionParts = strCondition.trim().split(" ");
                return new Precondition(conditionParts[0], PreconditionType.byIdentifier(conditionParts[1]), conditionParts[2]);
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
