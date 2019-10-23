package voronovo.koi2019.generation.test;

import lombok.Data;
import voronovo.koi2019.entity.TestItem;
import voronovo.koi2019.generation.test.api.TestBuilder;
import voronovo.koi2019.generation.util.ConstantsHolder;
import voronovo.koi2019.generation.util.TestBuilderUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class MultiTestBuilder implements TestBuilder {
    private List<TestBuilder> builders;

    @Override
    public List<TestItem> buildBatch(Integer amount, Integer incorrectAnswers) {
        return IntStream
                .range(0, Optional.ofNullable(amount).orElse(ConstantsHolder.DEFAULT_BATCH_SIZE))
                .mapToObj(i -> build(Optional.ofNullable(incorrectAnswers).orElse(ConstantsHolder.DEFAULT_INCORRECT_ANSWERS)))
                .collect(Collectors.toList());
    }

    @Override
    public TestItem build(int incorrectAnswers) {
        return getRandomBuilder().build(incorrectAnswers);
    }

    private TestBuilder getRandomBuilder() {
        return TestBuilderUtil.getRandomElement(builders);
    }
}
