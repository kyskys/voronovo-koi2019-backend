package voronovo.koi2019.generation.test.pow;

import lombok.Data;
import lombok.EqualsAndHashCode;
import voronovo.koi2019.generation.test.AbstractCodeWrittenBuilder;

import static voronovo.koi2019.generation.util.RegExpUtil.handleSigns;

@EqualsAndHashCode(callSuper = true)
@Data
public class SameDegreeRevertPowTestBuilder extends AbstractCodeWrittenBuilder {
    private String pattern = "([+-]?)(\\d+)\\^([+-]?)(\\d+)([\\/*]?)([+-]?)(\\d+)\\^([+-]?)(\\d+)";
    private String sample = "[var1]^[var2]/*[var3]^[var2]";
    //2^6/2^3
    //(6/3)^2

    @Override
    protected void updateOptions() {
//        getOptions().put("answer2", getRandomVariable() + getRandomVariable());
//        getOptions().put("answer3", getRandomVariable() - getRandomVariable());
    }

    @Override
    public String generateOption(String option, String generatorValue) {
        String degree = getRandomVariable();
        return getRandomVariable() + "^" + degree + handleSigns("*/") + getRandomVariable() + "^" + degree;
    }

    @Override
    protected String handleExpression(String expression) {
        String base = expression.replaceFirst(getPattern(), "$1$2$5$6$7"); //вычленяем var1*/var2
        if (base.contains("/")) { //если присутствует деление
            String newVariable = getCalculator().calculateExpression(base.replace("/", "*")); //заменяем на var1*var2 и вычисляем
            base = expression.replaceFirst(pattern, "$1$2"); //для записи ответа используем var1
            setExpression(expression.replaceFirst(getPattern(),  base + "^$8$9"));
            getOptions().put("answer", newVariable); //сохраняем
            return expression.replaceFirst("[+-]?\\d+", newVariable);

        } else { //если присутствует умножение - ничего не делаем
            base = getCalculator().calculateExpression(base);
            getOptions().put("answer", base);
            setExpression(expression.replaceFirst(getPattern(), base + "^$8$9"));
            return expression;
        }
    }
}
