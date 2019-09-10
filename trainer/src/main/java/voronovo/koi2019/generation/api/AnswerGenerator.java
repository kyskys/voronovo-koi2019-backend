package voronovo.koi2019.generation.api;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public interface AnswerGenerator {

    default List<String> generateAnswers(String answer, int numberOfAdditionalOptions) {
        int ans = Integer.parseInt(answer);
        int min = ans - 4;
        int max = ans + 4;
        Set<Integer> set = new HashSet<>();
        set.add(ans);
        while (numberOfAdditionalOptions != 0) {
            int var = (int) Math.round(Math.random() * (max - min) + min);
            if (!set.contains(var)) {
                set.add(var);
                numberOfAdditionalOptions--;
            }
        }
        return set.stream().unordered().map(Objects::toString).collect(Collectors.toList());
    }
}
