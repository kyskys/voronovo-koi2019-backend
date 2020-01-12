package voronovo.koi2019.generation.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

public class ConstantsHolder {
    /**
     * Переменная должна иметь порядковый номер и находиться в квадратных скобках
     * пример: [var1]
     */
    public static final String VARIABLE_REGEX = "(?<=\\[)var[\\d]+(?=\\])";
    public static final String SEPARATOR = "\\|";
    public static final String NUMBER_REGEX = "([+-]?\\d+)";
    public static final String DOUBLE_REGEX = "([+-]?\\d+\\.\\d+)";
    public static final String ADDITIONAL_SEPARATOR = "<>";
    public static final String BUILDERS_SEPARATOR = "\\\\";
    public static final Integer DEFAULT_BATCH_SIZE = 6;
    public static final Integer DEFAULT_INCORRECT_ANSWERS = 3;
    public static final String SIGN_REGEX = "[+-/*()]";
    public static final String IS_INTEGER_REGEX = "-?\\d+(\\.\\d+)+";
    public static final List<String> SUPPORTED_IMAGES_TYPES = Arrays.asList(MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, "image/bmp");
    private static final long BYTES_IN_KBYTE = 1024L;
    private static final long KBYTES_IN_MBYTE = 1024L;
    public static final long MAX_IMAGE_SIZE_IN_MBYTES = 2L;
    public static final long IMAGE_MAX_SIZE_IN_BYTES = MAX_IMAGE_SIZE_IN_MBYTES * KBYTES_IN_MBYTE * BYTES_IN_KBYTE;

}
