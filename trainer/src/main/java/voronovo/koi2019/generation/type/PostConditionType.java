package voronovo.koi2019.generation.type;

import voronovo.koi2019.condition.PostCondition;
import voronovo.koi2019.entity.Test;
import voronovo.koi2019.generation.test.DefaultTestBuilder;

import java.util.*;
import java.util.stream.Collectors;

import static voronovo.koi2019.generation.util.ConstantsHolder.*;

public enum PostConditionType {
    NONE("none") {
        @Override
        public boolean isInvalid(Test test) {
            return false;
        }

        @Override
        public void modify(Test test, PostCondition condition, DefaultTestBuilder builder) {

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
        public void modify(Test test, PostCondition condition, DefaultTestBuilder builder) {
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
        public void modify(Test test, PostCondition condition, DefaultTestBuilder builder) {
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
    },
    NOT_EQUAL("not equal") {
        @Override
        public boolean isInvalid(Test test) {
            return test.getCorrectAnswer().equals(test.getExpression());
        }

        @Override
        public void modify(Test test, PostCondition condition, DefaultTestBuilder builder) {
            Map<String, Integer> variablesMap = builder.getVariablesMap();
            Set<Integer> uniqueSet = new HashSet<>();
            boolean isUnique = variablesMap //берём мапу переменных
                    .keySet() //названия переменных
                    .stream()
                    .filter(variable -> condition.getValue().contains(variable)) //оставляем те названия, что указаны в настройке
                    .map(variablesMap::get) //берём их числовые значения
                    .allMatch(uniqueSet::add); //добавляем в Set, если не добавило - значит есть одинаковые числа
            if(!isUnique) { //в isInvalid(Test test) откидываем этот тест, делая такую замену и проверку
                test.setExpression("invalid");
                test.setCorrectAnswer("invalid");
            }
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
                .orElseThrow(() -> new IllegalArgumentException("invalid post condition identifier for " + condition))
                .getCondition(condition.trim());
    }

    public abstract boolean isInvalid(Test test);

    public abstract void modify(Test test, PostCondition condition, DefaultTestBuilder builder);

    public abstract PostCondition getCondition(String value);
}