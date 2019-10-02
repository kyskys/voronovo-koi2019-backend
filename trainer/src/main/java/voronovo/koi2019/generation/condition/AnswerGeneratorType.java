package voronovo.koi2019.generation.condition;

import voronovo.koi2019.generation.calculator.Calculator;
import voronovo.koi2019.generation.calculator.JavaScriptCalculator;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.IntStream;

public enum AnswerGeneratorType {
    ANSWER_INTERVAL("interval") {
        @Override
        public String apply(String option, AnswerGenerator generator, Calculator calculator) {
            if(((JavaScriptCalculator) calculator).isInteger()) {//TODO: заменить на генерик?
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
        public String apply(String option, AnswerGenerator generator, Calculator calculator) {
            if(((JavaScriptCalculator) calculator).isInteger()) {
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
    };

    public abstract <T extends Calculator> String apply(String option, AnswerGenerator generator, Calculator calculator);

    private final String identifier;

    AnswerGeneratorType(String identifier) {
        this.identifier = identifier;
    }

    public static AnswerGenerator find(String condition) {
        return EnumSet.allOf(AnswerGeneratorType.class).stream().filter(value -> condition.contains(value.identifier)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid answer generator identifier"))
                .getAnswerGenerator(condition.trim());
    }

    public abstract AnswerGenerator getAnswerGenerator(String value);
}
