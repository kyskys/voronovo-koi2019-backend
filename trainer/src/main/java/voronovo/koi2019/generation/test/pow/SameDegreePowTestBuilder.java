package voronovo.koi2019.generation.test.pow;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static voronovo.koi2019.generation.util.RegExpUtil.handleSigns;

@EqualsAndHashCode(callSuper = true)
@Data
public class SameDegreePowTestBuilder extends AbstractPowBuilder {
    private String pattern = "([+-]?)(\\d+)\\^([+-]?)(\\d+)([\\/*]?)([+-]?)(\\d+)\\^([+-]?)(\\d+)";
    private String sample = "[var1]^[var2]/*[var3]^[var2]";

    @Override
    protected void updateOptions() {
//        getOptions().put("answer2", getRandomVariable() + getRandomVariable());
//        getOptions().put("answer3", getRandomVariable() - getRandomVariable());
    }

    @Override
    public String generateOption() {
        return "(" + getRandomVariable() + handleSigns("/*") + getRandomVariable() + ")^" + getRandomVariable();
    }

    @Override
    protected String handleExpression(String expression) {
        String base = expression.replaceFirst(getPattern(), "$1$2$5$6$7"); //вычленяем var1*/var2
        if (base.contains("/")) { //если присутствует деление
            String newVariable = getCalculator().calculateExpression(base.replace("/", "*")); //заменяем на var1*var2 и вычисляем
            base = expression.replaceFirst(pattern, "$1$2"); //для записи ответа используем var1
            setExpression(expression.replaceFirst("[+-]?\\d+", newVariable)); //в итоговом выражении заменяем var1 на результат умножения
            getOptions().put("answer", Integer.valueOf(newVariable)); //сохраняем
        } else { //если присутствует умножение - ничего не делаем
            base = getCalculator().calculateExpression(base);
            getOptions().put("answer", Integer.valueOf(base));
        }
        return expression.replaceFirst(getPattern(),  base + "^$8$9");
    }
}
