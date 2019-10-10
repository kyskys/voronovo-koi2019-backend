package voronovo.koi2019.generation.test;

import lombok.Data;
import voronovo.koi2019.generation.test.api.TestBuilder;

import java.util.List;

@Data
public abstract class AbstractTestBuilder implements TestBuilder {

    public abstract String getFinalExpression();

    public abstract List<String> generateAnswers(String answer, int incorrectAnswers);

    public abstract String getAnswer(String expression);
}
