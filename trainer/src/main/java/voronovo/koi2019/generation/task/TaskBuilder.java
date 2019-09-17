package voronovo.koi2019.generation.task;

import voronovo.koi2019.generation.api.AnswerGenerator;
import voronovo.koi2019.generation.api.Calculator;
import voronovo.koi2019.generation.condition.Precondition;
import voronovo.koi2019.generation.condition.PreconditionParser;
import voronovo.koi2019.generation.util.RegExpUtil;
import voronovo.koi2019.generation.util.ConstantsHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TaskBuilder {
    private Map<String, Integer> variablesMap = new HashMap<>();
    private List<Precondition> preconditions;
    private List<Precondition> postconditions;
    private String expression;
    private Calculator calculator;
    private AnswerGenerator answerGenerator;


    public TaskBuilder(String sample, Calculator calculator, AnswerGenerator answerGenerator) {
        String[] data = sample.split(ConstantsHolder.SEPARATOR);
        this.expression = data[0].trim();
        RegExpUtil.findAllUnique(expression, ConstantsHolder.VARIABLE_REGEX).forEach(value -> variablesMap.put(value, null));
        this.preconditions = PreconditionParser.parse(data[1].trim());
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

    public List<Task> buildBatch(Integer amount, Integer incorrectAnswers) {
        return IntStream
                .range(0, Optional.ofNullable(amount).orElse(ConstantsHolder.DEFAULT_BATCH_SIZE))
                .mapToObj(i -> build(Optional.ofNullable(incorrectAnswers).orElse(ConstantsHolder.DEFAULT_INCORRECT_ANSWERS)))
                .collect(Collectors.toList());
    }
}
