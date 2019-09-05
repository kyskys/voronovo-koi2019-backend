package voronovo.koi2019.util;

public class RegexConst {
    /**
     * Переменная должна иметь порядковый номер и находиться в квадратных скобках
     * пример: [var1]
     */
    public static final String VARIABLE_REGEX = "(?<=\\[)var[\\d]+(?=\\])";
}
