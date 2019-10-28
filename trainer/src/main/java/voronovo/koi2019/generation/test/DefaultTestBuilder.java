package voronovo.koi2019.generation.test;

import lombok.*;
import voronovo.koi2019.entity.TestItem;
import voronovo.koi2019.generation.calculator.Calculator;
import voronovo.koi2019.generation.condition.PostCondition;
import voronovo.koi2019.generation.condition.PreCondition;
import voronovo.koi2019.generation.test.api.OptionGenerator;
import voronovo.koi2019.generation.test.api.TestBuilder;
import voronovo.koi2019.generation.util.RegExpUtil;
import voronovo.koi2019.generation.util.ConstantsHolder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class DefaultTestBuilder extends AbstractTestBuilder implements TestBuilder, OptionGenerator {
    private Map<String, String> variablesMap = new HashMap<>();
    private String expression;
    private @NonNull String sample;
    private @NonNull List<PreCondition> preConditions;
    private @NonNull List<PostCondition> postConditions;
    private @NonNull List<AnswerGenerator> answerGenerators;
    private @NonNull Calculator calculator;

    public void initVariables() {
        for (PreCondition precondition : preConditions) {
            variablesMap.put(precondition.getTarget(), precondition.getPreconditionType().generateValue(precondition.getValue(), this));
        }
    }

    @Override
    public TestItem build(int incorrectAnswers) {
        initVariables();
        setExpression(getFinalExpression(getSample()));
        String answer = getAnswer(getExpression());
        List<String> answers = generateAnswers(answer, incorrectAnswers);
        TestItem testItem = new TestItem(expression, answers, answer);
        if (this.postConditions != null) {
            boolean needToRestart = this.postConditions
                    .stream()
                    .peek(condition -> condition.getType().modify(testItem, condition, this))
                    .map(condition -> condition.getType().isInvalid(testItem))
                    .reduce((b1, b2) -> b1 || b2)
                    .orElseThrow(() -> new IllegalArgumentException("wrong post conditions configuration"));
            return needToRestart ? build(incorrectAnswers) : testItem;
        };
        return testItem;
    }

    @Override
    public String getAnswer(String expression) {
        String result = calculator.calculateExpression(expression);
        variablesMap.put("answer", result);
        return result;
    }

    @Override
    public List<String> generateAnswers(String answer, int incorrectAnswers) {
        List<String> options = new ArrayList<>(Collections.nCopies(incorrectAnswers, answer));
            IntStream.range(0, incorrectAnswers).forEach(i -> {
                String option = options.get(i);
                do {
                    for (AnswerGenerator generator : answerGenerators) {
                        option = generator.getType().apply(option, generator, this);
                    }
                } while (options.contains(option) || option.equals(answer));
                options.set(i, option);
            });
        return options;
    }

    @Override
    public String getFinalExpression(String sample) {
        String result = sample;
        result = RegExpUtil.handleSigns(result);
        result = replaceVariables(result);
        result = RegExpUtil.handleNegativeSigns(result);
        return result;
    }

    public String getAdvancedFinalExpression(String sample) {
        String result = sample;
        result = replaceVariables(result);
        result = RegExpUtil.handleNegativeSigns(result);
        return result;
    }

    public String replaceVariables(String result) {
        for (Map.Entry<String, String> entry : variablesMap.entrySet()) {
            result = result.replaceAll("\\[" + entry.getKey() + "]", entry.getValue().toString());
        }
        return result;
    }

    @Override
    public List<TestItem> buildBatch(Integer amount, Integer incorrectAnswers) {
        return IntStream
                .range(0, Optional.ofNullable(amount).orElse(ConstantsHolder.DEFAULT_BATCH_SIZE))
                .mapToObj(i -> build(Optional.ofNullable(incorrectAnswers).orElse(ConstantsHolder.DEFAULT_INCORRECT_ANSWERS)))
                .collect(Collectors.toList());
    }

    @Override
    public String generateOption(String option, String generatorValue) {
        option = generatorValue.replace("[option]", option);
        return calculator.calculateExpression(option);
    }
}
