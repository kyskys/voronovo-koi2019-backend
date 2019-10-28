package voronovo.koi2019.generation.type;

import voronovo.koi2019.generation.condition.PostCondition;
import voronovo.koi2019.entity.TestItem;
import voronovo.koi2019.generation.test.DefaultTestBuilder;

import java.util.*;
import java.util.stream.Collectors;

import static voronovo.koi2019.generation.util.ConstantsHolder.*;

public enum PostConditionType {
    NONE("none") {
        @Override
        public boolean isInvalid(TestItem testItem) {
            return false;
        }

        @Override
        public void modify(TestItem testItem, PostCondition condition, DefaultTestBuilder builder) {

        }

        @Override
        public PostCondition getCondition(String value) {
            return new PostCondition(this, null);
        }
    },
    IS_INTEGER("integer") {
        @Override
        public boolean isInvalid(TestItem testItem) {
            return testItem.getCorrectAnswer().matches(IS_INTEGER_REGEX);
        }

        @Override
        public void modify(TestItem testItem, PostCondition condition, DefaultTestBuilder builder) {
            testItem.setAllOptions(testItem.getAllOptions().stream().map(option -> option.matches(IS_INTEGER_REGEX) ? option.split("\\.")[0] : option).collect(Collectors.toList()));
        }

        @Override
        public PostCondition getCondition(String value) {
            return new PostCondition(this, null);
        }
    },
    PATTERN("pattern") {
        @Override
        public boolean isInvalid(TestItem testItem) {
            return false;
        }

        @Override
        public void modify(TestItem testItem, PostCondition condition, DefaultTestBuilder builder) {
            String[] patternParameters = condition.getValue().split(" ");
            String sample = builder.getAdvancedFinalExpression(builder.replaceVariables(patternParameters[1].replace("[answer]", testItem.getCorrectAnswer())));
            String correctAnswer = String.valueOf(builder.getFinalExpression(patternParameters[2]));
            List<String> allOptions = builder.generateAnswers(correctAnswer, testItem.getAllOptions().size());
            testItem.setExpression(sample);
            testItem.setCorrectAnswer(correctAnswer);
            testItem.setAllOptions(allOptions);
        }

        @Override
        public PostCondition getCondition(String value) {
            return new PostCondition(this, value);
        }
    },
    NOT_EQUAL("verify") {
        @Override
        public boolean isInvalid(TestItem testItem) {
            return testItem.getCorrectAnswer().equals(testItem.getExpression());
        }

        @Override
        public void modify(TestItem testItem, PostCondition condition, DefaultTestBuilder builder) {
            String result = builder.getAdvancedFinalExpression(condition.getValue().split(" ")[1]);
            result = builder.getCalculator().calculateExpression(result);
            if(!result.equals("true")) {
                testItem.setExpression("invalid");
                testItem.setCorrectAnswer("invalid");
            }
//            Map<String, String> variablesMap = builder.getVariablesMap();
//            Set<String> uniqueSet = new HashSet<>();
//            boolean isUnique = variablesMap //берём мапу переменных
//                    .keySet() //названия переменных
//                    .stream()
//                    .filter(variable -> condition.getValue().contains(variable)) //оставляем те названия, что указаны в настройке
//                    .map(variablesMap::get) //берём их числовые значения
//                    .allMatch(uniqueSet::add); //добавляем в Set, если не добавило - значит есть одинаковые числа
//            if(!isUnique) { //в isInvalid(Test test) откидываем этот тест, делая такую замену и проверку
//                test.setExpression("invalid");
//                test.setCorrectAnswer("invalid");
//            }
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

    public abstract boolean isInvalid(TestItem testItem);

    public abstract void modify(TestItem testItem, PostCondition condition, DefaultTestBuilder builder);

    public abstract PostCondition getCondition(String value);
}