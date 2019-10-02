package voronovo.koi2019.generation.condition;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public enum PreConditionType {
    EQ("==") {
        @Override
        public int generateValue(String value) {
            return Integer.parseInt(value);
        }

        @Override
        public PreCondition getCondition(String value) {
            String[] conditionParts = value.trim().split(" ");
            return new PreCondition(conditionParts[0], this, conditionParts[2]);
        }
    },
    BETWEEN("between") {
        @Override
        public int generateValue(String value) {
            String interval = Stream
                    .of(value.split("and"))
                    .sorted((o1, o2) -> ThreadLocalRandom.current().nextInt(-1, 2))
                    .findAny()
                    .get();
            String[] split = interval.split(";");
            int min = Integer.parseInt(split[0]);
            int max = Integer.parseInt(split[1]);
            return (int) Math.round(Math.random() * (max - min) + min);
        }

        @Override
        public PreCondition getCondition(String value) {
            String[] conditionParts = value.trim().split(" ");
            return new PreCondition(conditionParts[0], this, conditionParts[2]);
        }
    };

    private final String identifier;

    PreConditionType(String identifier) {
        this.identifier = identifier;
    }

    public static PreCondition find(String condition) {
        return EnumSet.allOf(PreConditionType.class).stream().filter(value -> condition.contains(value.identifier)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid condition identifier")).getCondition(condition);
    }

    public abstract int generateValue(String value);
    public abstract PreCondition getCondition(String value);
}
