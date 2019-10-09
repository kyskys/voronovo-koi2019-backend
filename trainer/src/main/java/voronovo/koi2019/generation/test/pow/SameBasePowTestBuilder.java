package voronovo.koi2019.generation.test.pow;

import lombok.Data;
import lombok.EqualsAndHashCode;
import voronovo.koi2019.generation.test.api.TestBuilderPart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@EqualsAndHashCode(callSuper = true)
@Data
public class SameBasePowTestBuilder extends AbstractPowBuilder {
    private String pattern = "([+-]?)(\\d+)\\^([+-]?)(\\d+)([\\/*]?)([+-]?)(\\d+)\\^([+-]?)(\\d+)";
    private String sample = "[var1]^[var2]/*[var1]^[var3]";

    @Override
    protected void updateOptions() { //добавляем пару неверных в мн-во опций
        getOptions().put("answer2", getRandomVariable() + getRandomVariable());
        getOptions().put("answer3", getRandomVariable() - getRandomVariable());
    }

    @Override
    protected String generateOption() {
        return getRandomVariable() + "^" + getRandomVariable();
    }

    @Override
    protected String handleExpression(String expression) {
        String degree = getCalculator().calculateExpression(
                 expression.replaceFirst(getPattern(), "$3$4$5$7$8")
                        .replace("*", "+")
                        .replace("/", "-"));
        getOptions().put("answer", Integer.valueOf(degree));
        return expression.replaceFirst(getPattern(), "$1$2") + "^" + degree;
    }
}
