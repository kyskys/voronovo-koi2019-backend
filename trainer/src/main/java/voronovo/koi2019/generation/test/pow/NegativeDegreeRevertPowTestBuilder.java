package voronovo.koi2019.generation.test.pow;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NegativeDegreeRevertPowTestBuilder extends AbstractPowBuilder {
    private String pattern = "1/\\(([+-]?)(\\d+)\\^([+-]?)(\\d+)\\)";
    private String sample = "1/([var1]^[var2])";

    @Override
    protected void updateOptions() {
//        getOptions().put("answer2", getRandomVariable() + getRandomVariable());
//        getOptions().put("answer3", getRandomVariable() - getRandomVariable());
    }

    @Override
    public String generateOption() {
        return getRandomMinus() + getRandomVariable() + "^" + getRandomMinus() + getRandomVariable();
    }

    @Override
    protected String handleExpression(String expression) {
        return expression.replaceFirst(pattern, "$1$2^-$4");
    }

    private static String getRandomMinus() {
        return Math.random() > 0.5 ? "-" : "";
    }
}
