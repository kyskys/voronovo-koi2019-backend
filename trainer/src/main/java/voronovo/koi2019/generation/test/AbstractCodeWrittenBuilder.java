package voronovo.koi2019.generation.test;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import voronovo.koi2019.generation.test.DefaultTestBuilder;
import voronovo.koi2019.generation.test.api.OptionGenerator;
import voronovo.koi2019.generation.test.api.TestBuilderPart;
import voronovo.koi2019.generation.util.RegExpUtil;
import voronovo.koi2019.generation.util.TestBuilderUtil;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public abstract class AbstractCodeWrittenBuilder extends DefaultTestBuilder implements TestBuilderPart, OptionGenerator {
    protected Map<String, String> options;

    protected abstract void updateOptions();

    protected abstract String handleExpression(String expression);

    public abstract String getSample();

    @Override
    public String getFinalExpression(String sample) {
        initVariables();
        this.options = new HashMap<>(getVariablesMap());
        updateOptions();
        String result = RegExpUtil.handleSigns(sample);
        result = replaceVariables(result);
        //result = RegExpUtil.handleNegativeSigns(result);
        return result;
    }

    @Override
    public String getAnswer(String expression) {
        return handleExpression(expression);
    }

    protected String getRandomVariable() {
        return TestBuilderUtil.getRandomElement(options.values());
    }
}
