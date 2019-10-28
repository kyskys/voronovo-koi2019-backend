package voronovo.koi2019.generation.util;

import voronovo.koi2019.generation.condition.PostCondition;
import voronovo.koi2019.generation.condition.PreCondition;
import voronovo.koi2019.generation.calculator.Calculator;
import voronovo.koi2019.generation.calculator.JavaScriptCalculator;
import voronovo.koi2019.generation.test.AbstractCodeWrittenBuilder;
import voronovo.koi2019.generation.test.AnswerGenerator;
import voronovo.koi2019.generation.type.*;
import voronovo.koi2019.generation.test.MultiTestBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static voronovo.koi2019.generation.util.ConstantsHolder.ADDITIONAL_SEPARATOR;
import static voronovo.koi2019.generation.util.ConstantsHolder.BUILDERS_SEPARATOR;

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

    public static AbstractCodeWrittenBuilder getPowBuilder(Class<? extends AbstractCodeWrittenBuilder> clazz, String preConditions, String postConditions) {
        try {
            AbstractCodeWrittenBuilder builder = clazz.newInstance();
            builder.setSample(TestBuilderUtil.getGeneratorSample(builder.getSample()));
            builder.setPreConditions(TestBuilderUtil.getPreConditions(preConditions));
            builder.setPostConditions(TestBuilderUtil.getPostConditions(postConditions));
            builder.setAnswerGenerators(TestBuilderUtil.getAnswerGenerators("modify=0 0"));
            builder.setCalculator(new JavaScriptCalculator(CalculatorType.JAVASCRIPT, true));
            return builder;
        } catch (InstantiationException | IllegalAccessException e) {
            throw getException("pow builder", "", e);
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

    public static <T> T getRandomElement(Collection<T> collection) {
        return collection
                .stream()
                .skip((int) (Math.random() * collection.size()))
                .findFirst()
                .get();
    }

    public static String abs(String str) {
        return str.replace("-", "");
    }

    public static MultiTestBuilder getMultiBuilder(String sample) {
        try {
            MultiTestBuilder builder = new MultiTestBuilder();
            String[] builders = sample.split(BUILDERS_SEPARATOR);
            builder.setBuilders(
                    Arrays.stream(builders)
                            .map(TestBuilderType::find)
                            .collect(Collectors.toList()));
            return builder;
        } catch (Exception e) {
            throw getException("multi test builder", sample, e);
        }
    }

    private static IllegalArgumentException getException(String message, String value, Exception e) {
        return new IllegalArgumentException("error while parsing " + message + " \"" + value + "\"", e);
    }
}
