package voronovo.koi2019.generation.test.pow;

import com.sun.javafx.collections.ImmutableObservableList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import voronovo.koi2019.generation.test.DefaultTestBuilder;
import voronovo.koi2019.generation.test.api.TestBuilderPart;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@EqualsAndHashCode(callSuper = true)
@Data
public class PowInPowTestBuilder extends AbstractPowBuilder implements TestBuilderPart {
    private String pattern = "(([+-]?)(\\d+)\\^([+-]?)(\\d+))\\^([+-]?)(\\d+)";
    private String sample = "([var1]^[var2])^[var3]";

//    private List<String> incorrectOptions = Arrays.asList(
//            "[answer2]^[var3]", "[var1]^[answer2]", "[var1]^[answer]", "[var1]^[var2]",
//            "[var2]^[var3]", "[var2]^[var1]", "[var2]^[answer]", "[var2]^[answer2]",
//            "[var3]^[answer2]", "[var3]^[var1]", "[var3]^[answer]", "[var3]^[var2]",
//            "[answer]^[var1]", "[answer]^[var2]", "[answer]^[var3]", "[answer]^[answer2]"
//    );

    @Override
    public String getFinalExpression() {
        initVariables(); //инитим переменные из sample и preconditions
        String result = replaceVariables(getExpression()); //заменяем
        String degree = getCalculator().calculateExpression(
                result.replaceFirst(pattern, "$3$4*$5$6") //[var2]*[var3]
                        .replace("*+", "*")); //1*+2 -> 1*2
        getOptions().put("answer", Integer.valueOf(degree)); //добавляем правильную степень и пару неверных в мн-во переменных
        getOptions().put("answer2", getRandomVariable() + getRandomVariable());
        getOptions().put("answer3", getRandomVariable() - getRandomVariable());
        return result.replaceFirst(pattern, "$1") + "^" + degree;
    }

    @Override
    public List<String> generateAnswers(String answer, int incorrectAnswers) {
        return IntStream.range(0, incorrectAnswers)
                .mapToObj(i -> getRandomVariable() + "^" + getRandomVariable())
                .collect(Collectors.toList());
    }
}
