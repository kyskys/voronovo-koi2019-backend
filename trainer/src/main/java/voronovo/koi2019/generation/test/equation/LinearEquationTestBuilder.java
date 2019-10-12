package voronovo.koi2019.generation.test.equation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import voronovo.koi2019.generation.test.AbstractCodeWrittenBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
public class LinearEquationTestBuilder extends AbstractCodeWrittenBuilder {
    private String pattern = "\\(([+-]?)(\\d+)\\^([+-]?)(\\d+)\\)\\^([+-]?)(\\d+)";
    private String sample = "[var1]^[var3]";

    @Override
    protected void updateOptions() {
//        getOptions().put("answer2", getRandomVariable() + getRandomVariable());
//        getOptions().put("answer3", getRandomVariable() - getRandomVariable());
    }

    @Override
    public String generateOption(String option, String generatorValue) {
        return getRandomVariable() + "^" + getRandomVariable();
    }

    @Override
    protected String handleExpression(String expression) {
        String degree = getCalculator().calculateExpression(
                expression.replaceFirst(pattern, "$3$4*$5$6") //[var2]*[var3]
                        .replace("*+", "*")); //1*+2 -> 1*2
        getOptions().put("answer", degree);
        return expression.replaceFirst(pattern, "$1$2") + "^" + degree;
    }
}
