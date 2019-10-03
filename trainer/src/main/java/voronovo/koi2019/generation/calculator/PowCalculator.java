package voronovo.koi2019.generation.calculator;

import lombok.Data;
import voronovo.koi2019.generation.condition.CalculatorType;

@Data
public class PowCalculator implements Calculator {

    @Override
    public String calculateExpression(String expression) {
        //a^n*/a^m=a^(m+-n)
        //a^n*/b^n=(a*/b)^n
        //TODO: сделать калькулятор
        return null;
    }

    private final CalculatorType calculatorType;
}