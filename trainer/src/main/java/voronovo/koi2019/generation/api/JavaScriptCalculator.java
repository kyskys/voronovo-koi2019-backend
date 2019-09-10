package voronovo.koi2019.generation.api;

import voronovo.koi2019.generation.util.RegExpUtil;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JavaScriptCalculator implements Calculator {

    @Override
    public String calculateExpression(String expression) {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        expression = RegExpUtil.convertExpressionToJs(expression);
        try {
            return engine.eval(expression).toString().split("\\.")[0];
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return "null";
    }
}
