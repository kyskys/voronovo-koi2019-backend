package voronovo.koi2019.generation.util;

import java.util.ArrayList;
import java.util.List;
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
}
