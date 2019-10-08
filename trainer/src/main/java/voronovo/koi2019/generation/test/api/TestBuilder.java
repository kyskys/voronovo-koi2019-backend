package voronovo.koi2019.generation.test.api;

import voronovo.koi2019.entity.Test;

import java.util.List;

public interface TestBuilder {
    List<Test> buildBatch(Integer amount, Integer incorrectAnswers);

    String getFinalExpression();

    List<String> generateAnswers(String answer, int incorrectAnswers);

    Test build(int incorrectAnswers);

    String getAnswer(String expression);
}
