package voronovo.koi2019.generation.test.pow;

import lombok.Data;
import lombok.EqualsAndHashCode;
import voronovo.koi2019.generation.test.api.TestBuilderPart;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@EqualsAndHashCode(callSuper = true)
@Data
public class SameBasePowTestBuilder extends AbstractPowBuilder implements TestBuilderPart {
    private String pattern = "([+-]?)(\\d+)\\^([+-]?)(\\d+)([\\/*]?)([+-]?)(\\d+)\\^([+-]?)(\\d+)";
    private String sample = "[var1]^[var2]/*[var1]^[var3]";

    @Override
    public String getFinalExpression() {
        String degree = getCalculator().calculateExpression(
                getExpression().replaceFirst(pattern, "$3$4$5$7$8")
                        .replace("*", "+")
                        .replace("/", "-"));
        return getExpression().replaceFirst(pattern, "$1$2") + "^" + degree;
    }

//    @Override
//    public boolean isApplicable() {
//        if (getExpression().matches(pattern)) {
//            String firstNumber = getExpression().replaceFirst(pattern, "$1$2");
//            String secondNumber = getExpression().replaceFirst(pattern, "$6$7");
//            return firstNumber.equals(secondNumber);
//        }
//        return false;
//    }

    @Override
    public List<String> generateAnswers(String answer, int incorrectAnswers) {
        return IntStream.range(0, incorrectAnswers)
                .mapToObj(i -> {
                    return getExpression()
                            .replaceFirst("[+-]?\\d+", getRandomVariable(answer).toString())
                            .replaceFirst("[+-]?\\d+", getRandomVariable(answer).toString());
                })
                .collect(Collectors.toList());
    }
}
