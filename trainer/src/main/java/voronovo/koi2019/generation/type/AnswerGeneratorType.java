package voronovo.koi2019.generation.type;

import voronovo.koi2019.generation.calculator.Calculator;
import voronovo.koi2019.generation.calculator.JavaScriptCalculator;
import voronovo.koi2019.generation.test.AnswerGenerator;
import voronovo.koi2019.generation.test.DefaultTestBuilder;
import voronovo.koi2019.generation.test.api.OptionGenerator;

import java.util.EnumSet;

public enum AnswerGeneratorType {
    ANSWER_INTERVAL("interval") {
        @Override
        public String apply(String option, AnswerGenerator generator, DefaultTestBuilder builder) {
            if (((JavaScriptCalculator) builder.getCalculator()).isInteger()) {//TODO: заменить на генерик?
                Integer toReturn = Integer.parseInt(option);
                Integer toMinus = (int) Math.round(Math.random() * Integer.parseInt(generator.getValue()));
                return String.valueOf(toReturn - toMinus);
            } else {
                Double toReturn = Double.parseDouble(option);
                Integer toMinus = (int) Math.round(Math.random() * Integer.parseInt(generator.getValue()));
                return String.valueOf(toReturn - toMinus);
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
            if (((JavaScriptCalculator) builder.getCalculator()).isInteger()) {
                int toReturn = Integer.parseInt(option);
                return String.valueOf(0 - toReturn);
            } else {
                double toReturn = Double.parseDouble(option);
                return String.valueOf(0 - toReturn);
            }
        }

        @Override
        public AnswerGenerator getAnswerGenerator(String value) {
            String[] typeAndValue = value.split(" ");
            String[] typeAndFrequency = typeAndValue[0].split("=");
            return new AnswerGenerator(this, Integer.parseInt(typeAndFrequency[1]), null);
        }
    },
    PATTERN("pattern") {
        @Override
        public <T extends Calculator> String apply(String option, AnswerGenerator generator, DefaultTestBuilder builder) {
            if(builder instanceof OptionGenerator) {
                return ((OptionGenerator) builder).generateOption();
            }
            return option;
        }

        @Override
        public AnswerGenerator getAnswerGenerator(String value) {
            return new AnswerGenerator(this, 0, null);
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
