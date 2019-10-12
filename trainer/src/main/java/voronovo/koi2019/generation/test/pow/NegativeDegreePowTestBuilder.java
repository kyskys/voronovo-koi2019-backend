package voronovo.koi2019.generation.test.pow;

import lombok.Data;
import lombok.EqualsAndHashCode;
import voronovo.koi2019.generation.test.AbstractCodeWrittenBuilder;

import static voronovo.koi2019.generation.util.TestBuilderUtil.abs;

@EqualsAndHashCode(callSuper = true)
@Data
public class NegativeDegreePowTestBuilder extends AbstractCodeWrittenBuilder {
    private String pattern = "([+-]?)(\\d+)\\^([+-]?)(\\d+)";
    private String sample = "[var1]^[var2]";

    @Override
    protected void updateOptions() {
//        getOptions().put("answer2", getRandomVariable() + getRandomVariable());
//        getOptions().put("answer3", getRandomVariable() - getRandomVariable());
    }

    @Override
    public String generateOption(String option, String generatorValue) {
        return "1/(" + getRandomMinus() + abs(getRandomVariable()).replace("-", "") + "^" + getRandomMinus() + abs(getRandomVariable()) + ")";
    }

    @Override
    protected String handleExpression(String expression) {
        return expression.replaceFirst(pattern, "1/($1$2^$4)");
    }

    private static String getRandomMinus() {
        return Math.random() > 0.5 ? "-" : "";
    }
}
