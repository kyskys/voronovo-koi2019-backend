package voronovo.koi2019.generation.test.pow;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import voronovo.koi2019.generation.calculator.JavaScriptCalculator;
import voronovo.koi2019.generation.condition.CalculatorType;
import voronovo.koi2019.generation.condition.PreCondition;
import voronovo.koi2019.generation.test.DefaultTestBuilder;
import voronovo.koi2019.generation.test.api.TestBuilderPart;
import voronovo.koi2019.generation.util.RegExpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public abstract class AbstractPowBuilder extends DefaultTestBuilder implements TestBuilderPart {
    protected Map<String, Integer> options;

    protected abstract void updateOptions();

    protected abstract String generateOption();

    protected abstract String handleExpression(String expression);

    public AbstractPowBuilder(String sample, List<PreCondition> preConditions) {
        super(sample,
                preConditions,
                new ArrayList<>(),
                new ArrayList<>(),
                new JavaScriptCalculator(CalculatorType.JAVASCRIPT, true));
    }

    @Override
    public String getFinalExpression() {
        initVariables();
        updateOptions();
        String result = RegExpUtil.handleSigns(getExpression());
        result = replaceVariables(result);
        result = RegExpUtil.handleNegativeSigns(result);
        return handleExpression(result);
    }

    @Override
    public List<String> generateAnswers(String answer, int incorrectAnswers) {
        return IntStream.range(0, incorrectAnswers)
                .mapToObj(i -> generateOption())
                .collect(Collectors.toList());
    }

    protected Integer getRandomVariable() {
        return options
                .values()
                .stream()
                .skip((int) (Math.random() * getVariablesMap().size()))
                .findFirst()
                .get();
    }
}
