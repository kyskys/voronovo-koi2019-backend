package voronovo.koi2019.generation.test.pow;

import lombok.NoArgsConstructor;
import voronovo.koi2019.generation.calculator.JavaScriptCalculator;
import voronovo.koi2019.generation.condition.CalculatorType;
import voronovo.koi2019.generation.condition.PreCondition;
import voronovo.koi2019.generation.test.DefaultTestBuilder;
import voronovo.koi2019.generation.test.api.TestBuilderPart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
public abstract class AbstractPowBuilder extends DefaultTestBuilder implements TestBuilderPart {
    public AbstractPowBuilder(String sample, List<PreCondition> preConditions) {
        super(sample,
                preConditions,
                new ArrayList<>(),
                new ArrayList<>(),
                new JavaScriptCalculator(CalculatorType.JAVASCRIPT, true));
    }

    protected Integer getRandomVariable(String answer) {
        return new HashMap<>(getVariablesMap())
                .put("answer", answer)
                .values()
                .stream()
                .skip((int) (Math.random() * getVariablesMap().size()))
                .findFirst()
                .get();
    }
}
