package voronovo.koi2019.generation.condition;

import voronovo.koi2019.entity.Test;

import java.util.EnumSet;

public enum PostConditionType {
    NONE("none") {
        @Override
        public boolean isValid(Test test) {
            return true;
        }

        @Override
        public PostCondition getCondition(String value) {
            return new PostCondition(this);
        }
    },
    IS_INTEGER("integer") {
        @Override
        public boolean isValid(Test test) {
            return test.getCorrectAnswer().matches("-?\\d+(\\.\\d+)+");
        }

        @Override
        public PostCondition getCondition(String value) {
            return new PostCondition(this);
        }
    };

    private final String identifier;

    PostConditionType(String identifier) {
        this.identifier = identifier;
    }

    public static PostCondition find(String condition) {
        return EnumSet.allOf(PostConditionType.class).stream().filter(value -> condition.contains(value.identifier)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid condition identifier for "+ condition))
                .getCondition(condition.trim());
    }

    public abstract boolean isValid(Test test);

    public abstract PostCondition getCondition(String value);
}