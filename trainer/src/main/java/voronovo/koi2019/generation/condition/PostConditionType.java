package voronovo.koi2019.generation.condition;

import voronovo.koi2019.entity.Test;

import java.util.EnumSet;

public enum PostConditionType {
    IS_INTEGER("integer") {
        @Override
        public boolean isValid(Test test) {
            return test.getCorrectAnswer().matches("-?\\d+(\\.\\d+)?");
        }
    };

    private final String identifier;

    PostConditionType(String identifier) {
        this.identifier = identifier;
    }

    public static PostConditionType byIdentifier(String identifier) {
        return EnumSet.allOf(PostConditionType.class).stream().filter(value -> value.identifier.equals(identifier)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid condition identifier"));
    }

    public abstract boolean isValid(Test test);
}