package voronovo.koi2019.generation.calculator;

import lombok.Data;
import voronovo.koi2019.generation.condition.CalculatorType;
import voronovo.koi2019.generation.util.RegExpUtil;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@Data
public class JavaScriptCalculator implements Calculator {

    @Override
    public String calculateExpression(String expression) {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        expression = RegExpUtil.convertExpressionToJs(expression);
        try {
            String result = engine.eval(expression).toString();
            return isInteger ? result.split("\\.")[0] : result;
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return "null";
    }

    private final CalculatorType calculatorType;
    private final boolean isInteger;
}
