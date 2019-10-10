package voronovo.koi2019.generation.test.api;

import voronovo.koi2019.entity.Test;

import java.util.List;

public interface TestBuilder {
    List<Test> buildBatch(Integer amount, Integer incorrectAnswers);

    Test build(int incorrectAnswers);
}
