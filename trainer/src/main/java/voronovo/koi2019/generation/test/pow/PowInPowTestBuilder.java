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
public class PowInPowTestBuilder extends AbstractPowBuilder {
    private String pattern = "\\(([+-]?)(\\d+)\\^([+-]?)(\\d+)\\)\\^([+-]?)(\\d+)";
    private String sample = "([var1]^[var2])^[var3]";

    @Override
    protected void updateOptions() {
//        getOptions().put("answer2", getRandomVariable() + getRandomVariable());
//        getOptions().put("answer3", getRandomVariable() - getRandomVariable());
    }

    @Override
    public String generateOption() {
        return getRandomVariable() + "^" + getRandomVariable();
    }

    @Override
    protected String handleExpression(String expression) {
        String degree = getCalculator().calculateExpression(
                expression.replaceFirst(pattern, "$3$4*$5$6") //[var2]*[var3]
                        .replace("*+", "*")); //1*+2 -> 1*2
        getOptions().put("answer", Integer.valueOf(degree));
        return expression.replaceFirst(pattern, "$1$2") + "^" + degree;
    }
}
