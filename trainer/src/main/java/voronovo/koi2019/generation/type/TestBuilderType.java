package voronovo.koi2019.generation.type;

import voronovo.koi2019.generation.test.DefaultTestBuilder;
import voronovo.koi2019.generation.test.api.TestBuilder;
import voronovo.koi2019.generation.test.pow.AbstractPowBuilder;
import voronovo.koi2019.generation.util.TestBuilderUtil;

import java.util.EnumSet;

import static voronovo.koi2019.generation.util.ConstantsHolder.SEPARATOR;

public enum TestBuilderType {
    DEFAULT("default") {
        @Override
        public TestBuilder getBuilder(String value) {
            String[] sampleParameters = value.split(SEPARATOR);
            return new DefaultTestBuilder(
                    TestBuilderUtil.getGeneratorSample(sampleParameters[0]),
                    TestBuilderUtil.getPreConditions(sampleParameters[1]),
                    TestBuilderUtil.getPostConditions(sampleParameters[2]),
                    TestBuilderUtil.getAnswerGenerators(sampleParameters[3]),
                    TestBuilderUtil.getCalculator(sampleParameters[4])
            );
        }
    },
    POW("pow") {
        @Override
        public TestBuilder getBuilder(String value) {
            return PowBuilderType.find(value);
        }
    };

    private final String identifier;

    TestBuilderType(String identifier) {
        this.identifier = identifier;
    }

    public static TestBuilder find(String sample) {
        return EnumSet.allOf(TestBuilderType.class).stream().filter(value -> sample.contains(value.identifier)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid test builder identifier for " + sample))
                .getBuilder(sample.trim());
    }

    public abstract TestBuilder getBuilder(String value);
}
