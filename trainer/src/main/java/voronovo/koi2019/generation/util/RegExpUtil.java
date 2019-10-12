package voronovo.koi2019.generation.util;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RegExpUtil {
    public static List<String> findAllUnique(String string, String regex) {
        return findAll(string, regex).stream().distinct().collect(Collectors.toList());
    }

    public static List<String> findAll(String string, String regex) {
        List<String> allMatches = new ArrayList<>();
        Matcher matcher = Pattern.compile(regex).matcher(string);
        while (matcher.find()) {
            allMatches.add(matcher.group());
        }
        return allMatches;
    }

    public static String handleSigns(String result) {
        String pattern = "[+\\-*/]{2,}";
        Matcher matcher = Pattern.compile(pattern).matcher(result);
        String toReturn = result;
        while (matcher.find()) {
            String signs = matcher.group();
            int randomSign = (int) Math.round(Math.random() * (signs.length() - 1) + 0);
            toReturn = matcher.replaceFirst(String.valueOf(signs.charAt(randomSign)));
            matcher.reset(toReturn);
        }
        return toReturn;
    }

    public static String convertExpressionToJs(String expression) {
        LinkedHashMap<String, String> variables = new LinkedHashMap<>();

        int index = 0;
        List<String> allUnique = findAllUnique(expression, "(?<=^| |-|\\+|\\/|\\*)\\d+\\^\\d+");

        while (allUnique.size() != 0) {
            for (int i = 0; i < allUnique.size(); i++, index++) {
                variables.put("jsvar" + index, allUnique.get(i));
                expression = expression.replaceAll(Pattern.quote(allUnique.get(i)), "jsvar" + index);
            }
            allUnique = findAllUnique(expression, "(?<=^| |-|\\+|\\/|\\*)\\d+\\^\\d+");
        }

        allUnique = findAllUnique(expression, "\\([^\\(]+?\\)\\^[\\d]*");
        while (allUnique.size() != 0) {
            for (int i = 0; i < allUnique.size(); i++, index++) {
                variables.put("jsvar" + index, allUnique.get(i));
                expression = expression.replaceAll(Pattern.quote(allUnique.get(i)), "jsvar" + index);
            }
            allUnique = findAllUnique(expression, "\\([^\\(]+?\\)\\^[\\d]*");
        }

        return replaceVariables(expression, variables.entrySet().iterator());
    }

    private static String replaceVariables(String value, Iterator<Map.Entry<String, String>> iterator) {
        Map.Entry<String, String> entry;
        if (iterator.hasNext()) {
            entry = iterator.next();
            value = replaceVariables(value, iterator);
        } else {
            return value;
        }
        String[] parts = entry.getValue().split("\\^");
        value = value.replaceAll(entry.getKey(), String.format("Math.pow(%s,%s)", parts[0], parts[1]));
        return value;
    }

    public static String handleNegativeSigns(String result) {
        String pattern = "([+\\-*])([+\\-*]\\d+)";
        return result.replaceAll(pattern, "$1($2)");
//        Matcher matcher = Pattern.compile(pattern).matcher(result);
//        while (matcher.find()) {
//            result = matcher.replaceFirst("(" + matcher.group() + ")");
//        }
//        return result;
    }

    public static String replaceAllSeparately(String str, String regexp, Function<String, String> modifier) {
        Matcher matcher = Pattern.compile(regexp).matcher(str);
        StringBuffer result = new StringBuffer();
        while(matcher.find()) {
            matcher.appendReplacement(result, modifier.apply(matcher.group()));
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
