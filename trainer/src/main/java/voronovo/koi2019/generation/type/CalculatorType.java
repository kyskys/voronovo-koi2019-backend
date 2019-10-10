package voronovo.koi2019.generation.type;

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

    public static Calculator find(String calculator) {
        return EnumSet.allOf(CalculatorType.class).stream().filter(value -> calculator.contains(value.identifier)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid calculator identifier for " + calculator))
                .getCalculator(calculator.trim());
    }

    public abstract Calculator getCalculator(String value);
}
