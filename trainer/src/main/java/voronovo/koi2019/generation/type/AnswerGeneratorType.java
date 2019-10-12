package voronovo.koi2019.generation.type;

import voronovo.koi2019.generation.calculator.Calculator;
import voronovo.koi2019.generation.calculator.JavaScriptCalculator;
import voronovo.koi2019.generation.test.AnswerGenerator;
import voronovo.koi2019.generation.test.DefaultTestBuilder;
import voronovo.koi2019.generation.test.api.OptionGenerator;
import voronovo.koi2019.generation.util.RegExpUtil;

import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static voronovo.koi2019.generation.util.ConstantsHolder.DOUBLE_REGEX;
import static voronovo.koi2019.generation.util.ConstantsHolder.NUMBER_REGEX;

public enum AnswerGeneratorType {
    ANSWER_INTERVAL("interval") {
        @Override
        public String apply(String option, AnswerGenerator generator, DefaultTestBuilder builder) {
            if (((JavaScriptCalculator) builder.getCalculator()).isInteger()) {//TODO: заменить на генерик?
                return RegExpUtil.replaceAllSeparately(option, NUMBER_REGEX, (number) -> {
                    Integer toReturn = Integer.parseInt(number);
                    Integer toMinus = (int) Math.round(Math.random() * Integer.parseInt(generator.getValue()));
                    return String.valueOf(toReturn - toMinus);
                });
            } else {
                return RegExpUtil.replaceAllSeparately(option, DOUBLE_REGEX, (number) -> {
                    Double toReturn = Double.parseDouble(number);
                    Integer toMinus = (int) Math.round(Math.random() * Integer.parseInt(generator.getValue()));
                    return String.valueOf(toReturn - toMinus);
                });
            }
        }

        @Override
        public AnswerGenerator getAnswerGenerator(String value) {
            String[] typeAndValue = value.split(" ");
            String[] typeAndFrequency = typeAndValue[0].split("=");
            return new AnswerGenerator(this, Integer.parseInt(typeAndFrequency[1]), typeAndValue[1]);
        }
    },
    NEGATIVE("negative") {
        @Override
        public String apply(String option, AnswerGenerator generator, DefaultTestBuilder builder) {
            if (((JavaScriptCalculator) builder.getCalculator()).isInteger()) {//TODO: заменить на генерик?
                return RegExpUtil.replaceAllSeparately(option, NUMBER_REGEX, (number) -> {
                    int toReturn = Integer.parseInt(number);
                    return String.valueOf(0 - toReturn);
                });
            } else {
                return RegExpUtil.replaceAllSeparately(option, DOUBLE_REGEX, (number) -> {
                    double toReturn = Double.parseDouble(number);
                    return String.valueOf(0 - toReturn);
                });
            }
        }

        @Override
        public AnswerGenerator getAnswerGenerator(String value) {
            String[] typeAndValue = value.split(" ");
            String[] typeAndFrequency = typeAndValue[0].split("=");
            return new AnswerGenerator(this, Integer.parseInt(typeAndFrequency[1]), null);
        }
    },
    PATTERN("modify") {
        @Override
        public <T extends Calculator> String apply(String option, AnswerGenerator generator, DefaultTestBuilder builder) {
            if(builder instanceof OptionGenerator) {
                return builder.generateOption(option, generator.getValue());
            }
            return option;
        }

        @Override
        public AnswerGenerator getAnswerGenerator(String value) {
            String[] typeAndValue = value.split(" ");
            String[] typeAndFrequency = typeAndValue[0].split("=");
            return new AnswerGenerator(this, Integer.parseInt(typeAndFrequency[1]), typeAndValue[1]);
        }
    };

    public abstract <T extends Calculator> String apply(String option, AnswerGenerator generator, DefaultTestBuilder builder);

    private final String identifier;

    AnswerGeneratorType(String identifier) {
        this.identifier = identifier;
    }

    public static AnswerGenerator find(String answer) {
        return EnumSet.allOf(AnswerGeneratorType.class).stream().filter(value -> answer.contains(value.identifier)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid answer generator identifier for" + answer))
                .getAnswerGenerator(answer.trim());
    }

    public abstract AnswerGenerator getAnswerGenerator(String value);
}
