package voronovo.koi2019.task;

import voronovo.koi2019.api.AnswerGenerator;
import voronovo.koi2019.api.Calculator;
import voronovo.koi2019.condition.Precondition;
import voronovo.koi2019.condition.PreconditionParser;
import voronovo.koi2019.util.RegExpUtil;
import voronovo.koi2019.util.RegexConst;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskBuilder {
    private Map<String, Integer> variablesMap = new HashMap<>();
    private List<Precondition> preconditions;
    private List<Precondition> postconditions;
    private String expression;
    private Calculator calculator;
    private AnswerGenerator answerGenerator;


    public TaskBuilder(String expression, String preconditions, Calculator calculator, AnswerGenerator answerGenerator) {
        this.expression = expression;
        RegExpUtil.findAllUnique(expression, RegexConst.VARIABLE_REGEX).forEach(value -> variablesMap.put(value, null));
        this.preconditions = PreconditionParser.parse(preconditions);
        this.calculator = calculator;
        this.answerGenerator = answerGenerator;
        initVariables();
    }

    private void initVariables() {
        for (Precondition precondition : preconditions) {
            variablesMap.put(precondition.getVariable(), precondition.getPreconditionType().generateValue(precondition.getValue()));
        }
    }

    public Task build(int incorrectAnswers) {
        String finalExpression = getFinalExpression();
        String answer = calculator.calculateExpression(finalExpression);
        List<String> answers = answerGenerator.generateAnswers(answer, incorrectAnswers);
        return new Task(finalExpression, answers, answer);
    }

    public String getFinalExpression() {
        String result = expression;
        for (Map.Entry<String, Integer> entry: variablesMap.entrySet()) {
            result = result.replaceAll("\\[" + entry.getKey() + "]", entry.getValue().toString());
        }
        return result;
    }
}
