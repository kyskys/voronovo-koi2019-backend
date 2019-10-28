package voronovo.koi2019.generation.type;

import voronovo.koi2019.generation.condition.PreCondition;
import voronovo.koi2019.generation.test.DefaultTestBuilder;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public enum PreConditionType {
    EQUAL("==") {
        @Override
        public String generateValue(String value, DefaultTestBuilder builder) {
           return builder.getCalculator().calculateExpression(builder.getFinalExpression(value));
        }

        @Override
        public PreCondition getCondition(String value) {
            String[] conditionParts = value.trim().split(" ");
            return new PreCondition(conditionParts[0], this, conditionParts[2]);
        }
    },
    NOT_EQUAL("!=") {
        @Override
        public String generateValue(String value, DefaultTestBuilder builder) {
            return "0";
        }

        @Override
        public PreCondition getCondition(String value) {
            return null;
        }
    },
    BETWEEN("between") {
        @Override
        public String generateValue(String value, DefaultTestBuilder builder) {
            String interval = Stream
                    .of(value.split("and"))
                    .sorted((o1, o2) -> ThreadLocalRandom.current().nextInt(-1, 2))
                    .findAny()
                    .get();
            String[] split = interval.split(";");
            int min = Integer.parseInt(split[0].trim());
            int max = Integer.parseInt(split[1].trim());
            return String.valueOf(Math.round(Math.random() * (max - min) + min));
        }

        @Override
        public PreCondition getCondition(String value) {
            String[] conditionParts = value.trim().split(" ", 3);
            return new PreCondition(conditionParts[0], this, conditionParts[2]);
        }
    };

    private final String identifier;

    PreConditionType(String identifier) {
        this.identifier = identifier;
    }

    public static PreCondition find(String condition) {
        return EnumSet.allOf(PreConditionType.class).stream().filter(value -> condition.contains(value.identifier)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid condition identifier for " + condition))
                .getCondition(condition.trim());
    }

    public abstract String generateValue(String value, DefaultTestBuilder builder);
    public abstract PreCondition getCondition(String value);
}
