package voronovo.koi2019.generation.condition;

import voronovo.koi2019.generation.calculator.Calculator;
import voronovo.koi2019.generation.calculator.JavaScriptCalculator;

import java.util.EnumSet;

public enum CalculatorType {
    JAVASCRIPT("javascript") {
        @Override
        public Calculator getCalculator(String value) {
            boolean isInteger = value.split(" ")[1].equals("integer");
            return new JavaScriptCalculator(this, isInteger);
        }
    };

    private final String identifier;

    CalculatorType(String identifier) {
        this.identifier = identifier;
    }

    public static Calculator find(String condition) {
        return EnumSet.allOf(CalculatorType.class).stream().filter(value -> condition.contains(value.identifier)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid answer generator identifier"))
                .getCalculator(condition.trim());
    }

    public abstract Calculator getCalculator(String value);
}
