package voronovo.koi2019.generation.test;

import voronovo.koi2019.entity.Test;
import voronovo.koi2019.generation.api.AnswerGenerator;
import voronovo.koi2019.generation.api.Calculator;
import voronovo.koi2019.generation.condition.PostCondition;
import voronovo.koi2019.generation.condition.PreCondition;
import voronovo.koi2019.generation.condition.ConditionsParser;
import voronovo.koi2019.generation.util.RegExpUtil;
import voronovo.koi2019.generation.util.ConstantsHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestBuilder {
    private Map<String, Integer> variablesMap = new HashMap<>();
    private List<PreCondition> preConditions;
    private List<PostCondition> postConditions;
    private String expression;
    private Calculator calculator;
    private AnswerGenerator answerGenerator;


    public TestBuilder(String sample, Calculator calculator, AnswerGenerator answerGenerator) {
        String[] data = sample.split(ConstantsHolder.SEPARATOR);
        this.expression = data[0].trim();
        RegExpUtil.findAllUnique(expression, ConstantsHolder.VARIABLE_REGEX).forEach(value -> variablesMap.put(value, null));
        this.preConditions = ConditionsParser.parsePre(data[1].trim());
        this.postConditions = data.length == 3 ? ConditionsParser.parsePost(data[2].trim()) : null;
        this.calculator = calculator;
        this.answerGenerator = answerGenerator;
    }

    private void initVariables() {
        for (PreCondition precondition : preConditions) {
            variablesMap.put(precondition.getTarget(), precondition.getPreconditionType().generateValue(precondition.getValue()));
        }
    }

    public Test build(int incorrectAnswers) {
        String finalExpression = getFinalExpression();
        String answer = calculator.calculateExpression(finalExpression);
        List<String> answers = answerGenerator.generateAnswers(answer, incorrectAnswers);
        return new Test(finalExpression, answers, answer);
    }

    public String getFinalExpression() {
        String result = expression;
        initVariables();
        result = RegExpUtil.handleSigns(result);
        for (Map.Entry<String, Integer> entry: variablesMap.entrySet()) {
            result = result.replaceAll("\\[" + entry.getKey() + "]", entry.getValue().toString());
        }
        result = RegExpUtil.handleNegativeSigns(result);
        return result;
    }

    public List<Test> buildBatch(Integer amount, Integer incorrectAnswers) {
        return IntStream
                .range(0, Optional.ofNullable(amount).orElse(ConstantsHolder.DEFAULT_BATCH_SIZE))
                .mapToObj(i -> build(Optional.ofNullable(incorrectAnswers).orElse(ConstantsHolder.DEFAULT_INCORRECT_ANSWERS)))
                .collect(Collectors.toList());
    }
}
