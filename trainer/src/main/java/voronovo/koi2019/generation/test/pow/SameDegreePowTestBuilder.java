package voronovo.koi2019.generation.test.pow;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static voronovo.koi2019.generation.util.RegExpUtil.handleSigns;

@EqualsAndHashCode(callSuper = true)
@Data
public class SameDegreePowTestBuilder extends AbstractPowBuilder {
    private String pattern = "\\(([+-]?)(\\d+)([\\/*])([+-]?)(\\d+)\\)\\^([+-]?)(\\d+)";
//    private String sample = "[var1]^[var2]/*[var3]^[var2]";
    private String sample = "([var1]/*[var2])^[var3]";

    @Override
    protected void updateOptions() {
        getOptions().put("answer2", getRandomVariable() + getRandomVariable());
        getOptions().put("answer3", getRandomVariable() - getRandomVariable());
    }

    @Override
    protected String generateOption() {
        return getRandomVariable() + "^" + getRandomVariable() + handleSigns("/*") + getRandomVariable() + "^" + getRandomVariable();
    }

    @Override
    protected String handleExpression(String expression) {
        String base = getCalculator().calculateExpression(
                expression.replaceFirst(getPattern(), "$1$2$3$4$5"));
        getOptions().put("answer", Integer.valueOf(base));
        return expression.replaceFirst(getPattern(), "$1$2^$3$4$5$6$7^$3$4");
    }
}
