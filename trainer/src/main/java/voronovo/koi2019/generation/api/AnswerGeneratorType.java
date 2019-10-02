package voronovo.koi2019.generation.api;

import voronovo.koi2019.entity.Test;
import voronovo.koi2019.generation.condition.PostConditionType;

import java.util.EnumSet;
import java.util.List;

public enum AnswerGeneratorType {
    ANSWER_INTERVAL("interval") {
        @Override
        public List<String> apply(String correctAnswer, List<String> allOptions) {
            return allOptions
                    .stream()
                    .filter()
        }
    };

    private final String identifier;

    AnswerGeneratorType(String identifier) {
        this.identifier = identifier;
    }

    public AnswerGeneratorType byIdentifier(String sample) {
        return EnumSet.allOf(AnswerGeneratorType.class).stream().filter(value -> sample.contains(value.identifier)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid condition identifier"));
    }

    public abstract List<String> apply(String correctAnswer, List<String> allOptions);
}
