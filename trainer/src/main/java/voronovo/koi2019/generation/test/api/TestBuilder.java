package voronovo.koi2019.generation.test.api;

import voronovo.koi2019.entity.TestItem;

import java.util.List;

public interface TestBuilder {
    List<TestItem> buildBatch(Integer amount, Integer incorrectAnswers);

    TestItem build(int incorrectAnswers);
}
