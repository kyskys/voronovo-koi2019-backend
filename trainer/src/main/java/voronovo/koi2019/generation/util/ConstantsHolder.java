package voronovo.koi2019.generation.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class ConstantsHolder {
    /**
     * Переменная должна иметь порядковый номер и находиться в квадратных скобках
     * пример: [var1]
     */
    public static final String VARIABLE_REGEX = "(?<=\\[)var[\\d]+(?=\\])";
    public static final String SEPARATOR = "\\|";
    public static final String ADDITIONAL_SEPARATOR = "<>";
    public static final Integer DEFAULT_BATCH_SIZE = 15;
    public static final Integer DEFAULT_INCORRECT_ANSWERS = 3;
}
