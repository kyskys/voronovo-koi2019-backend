package voronovo.koi2019.generation.test.pow;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static voronovo.koi2019.generation.util.RegExpUtil.handleSigns;

@EqualsAndHashCode(callSuper = true)
@Data
public class SameBaseRevertPowTestBuilder extends AbstractPowBuilder {
    private String pattern = "([+-]?)(\\d+)\\^([+-]?)(\\d+)([\\/*]?)([+-]?)(\\d+)\\^([+-]?)(\\d+)";
    private String sample = "[var1]^[var2]/*[var1]^[var3]";

    @Override
    protected void updateOptions() {
//        getOptions().put("answer2", getRandomVariable() + getRandomVariable());
//        getOptions().put("answer3", getRandomVariable() - getRandomVariable());
    }

    @Override
    public String generateOption() {
        Integer sameBase = getRandomVariable();
        return sameBase + "^" + getRandomVariable() + handleSigns("*/") + sameBase + "^" + getRandomVariable();
    }

    @Override
    protected String handleExpression(String expression) {
        String degree = getCalculator().calculateExpression(
                expression.replaceFirst(getPattern(), "$3$4$5$8$9")
                        .replace("*", "+")
                        .replace("/", "-"));
        getOptions().put("answer", Integer.valueOf(degree));
        setExpression(expression.replaceFirst(getPattern(), "$1$2") + "^" + degree);
        return expression;
    }
}
