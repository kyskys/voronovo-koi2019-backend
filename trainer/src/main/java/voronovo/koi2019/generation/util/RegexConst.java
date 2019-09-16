package voronovo.koi2019.generation.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RegexConst {
    /**
     * Переменная должна иметь порядковый номер и находиться в квадратных скобках
     * пример: [var1]
     */
    public static final String VARIABLE_REGEX = "(?<=\\[)var[\\d]+(?=\\])";
    public static final String SEPARATOR = "\\|";
}
