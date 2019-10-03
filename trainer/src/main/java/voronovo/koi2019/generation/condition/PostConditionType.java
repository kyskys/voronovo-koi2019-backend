package voronovo.koi2019.generation.condition;

import voronovo.koi2019.entity.Test;
import voronovo.koi2019.generation.test.TestBuilder;
import voronovo.koi2019.generation.util.ConstantsHolder;
import voronovo.koi2019.generation.util.RegExpUtil;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import static voronovo.koi2019.generation.util.ConstantsHolder.*;

public enum PostConditionType {
    NONE("none") {
        @Override
        public boolean isInvalid(Test test) {
            return false;
        }

        @Override
        public void modify(Test test, PostCondition condition, TestBuilder builder) {

        }

        @Override
        public PostCondition getCondition(String value) {
            return new PostCondition(this, null);
        }
    },
    IS_INTEGER("integer") {
        @Override
        public boolean isInvalid(Test test) {
            return test.getCorrectAnswer().matches(IS_INTEGER_REGEX);
        }

        @Override
        public void modify(Test test, PostCondition condition, TestBuilder builder) {
            test.setAllOptions(test.getAllOptions().stream().map(option -> option.matches(IS_INTEGER_REGEX) ? option.split("\\.")[0] : option).collect(Collectors.toList()));
        }

        @Override
        public PostCondition getCondition(String value) {
            return new PostCondition(this, null);
        }
    },
    PATTERN("pattern") {
        @Override
        public boolean isInvalid(Test test) {
            return false;
        }

        @Override
        public void modify(Test test, PostCondition condition, TestBuilder builder) {
            String[] patternParameters = condition.getValue().split(" ");
            String sample = builder.replaceVariables(patternParameters[1].replace("answer", test.getCorrectAnswer()));
            String correctAnswer = String.valueOf(builder.getVariablesMap().get(patternParameters[2]));
            List<String> allOptions = builder.generateAnswers(correctAnswer, test.getAllOptions().size());
            test.setExpression(sample);
            test.setCorrectAnswer(correctAnswer);
            test.setAllOptions(allOptions);
        }

        @Override
        public PostCondition getCondition(String value) {
            return new PostCondition(this, value);
        }
    };

    private final String identifier;

    PostConditionType(String identifier) {
        this.identifier = identifier;
    }

    public static PostCondition find(String condition) {
        return EnumSet.allOf(PostConditionType.class).stream().filter(value -> condition.contains(value.identifier)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid condition identifier for " + condition))
                .getCondition(condition.trim());
    }

    public abstract boolean isInvalid(Test test);

    public abstract void modify(Test test, PostCondition condition, TestBuilder builder);

    public abstract PostCondition getCondition(String value);
}