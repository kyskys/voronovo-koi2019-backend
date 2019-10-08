package voronovo.koi2019.generation.util;

import voronovo.koi2019.generation.calculator.Calculator;
import voronovo.koi2019.generation.condition.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static voronovo.koi2019.generation.util.ConstantsHolder.ADDITIONAL_SEPARATOR;

public class TestBuilderUtil {
    public static String getGeneratorSample(String value) {
        try {
            return value.trim();
        } catch (Exception e) {
            throw getException("generator sample", value, e);
        }
    }

    public static List<PreCondition> getPreConditions(String value) {
        try {
            return Arrays.stream(value.trim().split(ADDITIONAL_SEPARATOR)).map(PreConditionType::find).collect(Collectors.toList());
        } catch (Exception e) {
            throw getException("pre conditions", value, e);
        }
    }

    public static List<PostCondition> getPostConditions(String value) {
        try {
            return Arrays.stream(value.trim().split(ADDITIONAL_SEPARATOR)).map(PostConditionType::find).collect(Collectors.toList());
        } catch (Exception e) {
            throw getException("post conditions", value, e);
        }
    }

    public static Calculator getCalculator(String value) {
        try {
            return CalculatorType.find(value.trim());
        } catch (Exception e) {
            throw getException("calculator", value, e);
        }
    }

    public static List<AnswerGenerator> getAnswerGenerators(String value) {
        try {
            return Stream.of(value.trim().split(ADDITIONAL_SEPARATOR))
                    .map(AnswerGeneratorType::find)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw getException("answer generators", value, e);
        }
    }

    public static Integer getRandomVariable(Map<String, Integer> variablesMap, String answer) {
        return variablesMap
                .values()
                .stream()
                .skip((int) (Math.random() * variablesMap.size()))
                .findFirst()
                .get();
    }

    private static IllegalArgumentException getException(String message, String value, Exception e) {
        return new IllegalArgumentException("error while parsing " + message + " \"" + value + "\"", e);
    }
}
