import java.util.Arrays;
import java.util.Objects;

public interface Calculator {
    default String calculateExpression(String expression) {
        return Arrays.stream(expression.split("\\+")).map(value -> Integer.parseInt(value.trim())).reduce(Integer::sum)
                .map(Objects::toString).orElse("HZ");
    }
}
