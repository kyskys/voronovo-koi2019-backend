package voronovo.koi2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MathTaskMain {

    public static class RegexConst {
        /**
         * Переменная должна иметь порядковый номер и находиться в квадратных скобках
         * пример: [var1]
         */
        public static final String VARIABLE_REGEX = "(?<=\\[)var[\\d]+(?=\\])";
    }

    public static class RegExpUtil {
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

    public static class TaskContext {
        private Map<String, Integer> variablesMap = new HashMap<>();
        private List<Precondition> preconditions;
        private List<Precondition> postconditions;
        private String expression;

        public TaskContext(String expression, String preconditions) {
            this.expression = expression;
            RegExpUtil.findAllUnique(expression, RegexConst.VARIABLE_REGEX).forEach(value -> variablesMap.put(value, null));
            this.preconditions = PreconditionParser.parse(preconditions);
            handlePreconditions();
        }

        private void handlePreconditions() {
            for (Precondition precondition : preconditions) {
                //TODO тут возможность инициализации через другие переменные заложить через VariableInitListener
                variablesMap.put(precondition.getVariable(), precondition.getPreconditionType().generateValue(precondition.getValue()));
            }
        }

        public Map<String, Integer> getVariablesMap() {
            return variablesMap;
        }

        public String getResult() {
            String result = expression;
            for (Map.Entry<String, Integer> entry: variablesMap.entrySet()) {
                result = result.replaceAll("\\[" + entry.getKey() + "]", entry.getValue().toString());
            }
            return result;
        }

    }


    public enum PreconditionType {
        //        GT(">"),
        //        LT("<"),
        EQ("==") {
            @Override
            public int generateValue(String value) {
                return Integer.parseInt(value);
            }
        },
        //        NOT_EQ("!="),
        //        LT_OR_EQ("<="),
        //        GT_OR_EQ(">="),
        BETWEEN("between") {
            @Override
            public int generateValue(String value) {
                String[] split = value.split(";");
                int min = Integer.parseInt(split[0]);
                int max = Integer.parseInt(split[1]);
                return (int) Math.round(Math.random() * (max - min) + min);
            }
        };

        private final String identifier;

        PreconditionType(String identifier) {
            this.identifier = identifier;
        }

        public static PreconditionType byIdentifier(String identifier) {
            return EnumSet.allOf(PreconditionType.class).stream().filter(value -> value.identifier.equals(identifier)).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("invalid condition identifier"));
        }

        public abstract int generateValue(String value);
    }

    public class VariableInitListener {
        private TaskContext taskContext;
        private String variableId;
        private Precondition precondition;

        VariableInitListener create(String variableId, Precondition precondition, TaskContext taskContext) {
            return new VariableInitListener(variableId, precondition, taskContext);
        }

        private VariableInitListener(String variableId, Precondition precondition, TaskContext taskContext) {
            this.taskContext = taskContext;
            this.precondition = precondition;
            this.variableId = variableId;
        }

        public Optional<VariableInitListener> tryToCalculate() {
            return null;
//            precondition.getPreconditionType().generateValue();
        }
    }


    //на случай если нужно использовать несколько условий OR нужно отдельный чек, который внутри будет проверять всё что в нём лежит
    public interface Checker {
        boolean check(TaskContext taskContext);
    }

    public static class Precondition {
        private final String variable;
        private final PreconditionType preconditionType;
        private final String value;

        public Precondition(String variable, PreconditionType preconditionType, String value) {
            this.variable = variable;
            this.preconditionType = preconditionType;
            this.value = value;
        }


        public String getValue() {
            return value;
        }

        public PreconditionType getPreconditionType() {
            return preconditionType;
        }

        public String getVariable() {
            return variable;
        }
    }

    public static class PreconditionParser {
        public static List<Precondition> parse(String conditions) {
            if (conditions != null) {
                return Arrays.stream(conditions.split("<>")).map(strCondition -> {
                    String[] conditionParts = strCondition.trim().split(" ");
                    return new Precondition(conditionParts[0], PreconditionType.byIdentifier(conditionParts[1]), conditionParts[2]);
                }).collect(Collectors.toList());
            }
            return new ArrayList<>();
        }
    }

    public static class VaraibleInitQueue {
//
//        private void addListener(String variableId, ) {
//
//        }

    }

    public static void main(String[] args) {
//        voronovo.koi2019.util.RegExpUtil.findAll("dsnfkjsdnf kjnsd var skdf [var1] [var1]  [var222]", voronovo.koi2019.util.RegexConst.VARIABLE_REGEX).forEach(System.out::println);
//        voronovo.koi2019.util.RegExpUtil.findAllUnique("dsnfkjsdnf kjnsd var skdf [var1] [var1]  [var222]", voronovo.koi2019.util.RegexConst.VARIABLE_REGEX).forEach(System.out::println);
        TaskContext taskContext = new TaskContext("[var1] + [var2]", "var1 == 10 <> var2 between 50;55");
        int max = 100;
        int min = 10;
        System.out.println(Math.round(Math.random() * (max - min) + min));



//        String first = "a+b";
//        String conditions = "Положительный ответ";
//        int lvl = 3;
//        boolean onlyPositiveNumber = true;
//        boolean onlyPositiveAnswer = true;
//        String valueRange = getLvlModifier(lvl);
//        Map<String, Integer> vars = new HashMap<>();
//        while (!conditionsSatisfied(conditions, )) {
//            String[] split = valueRange.split("-");
//            int min = Integer.parseInt(split[0]);
//            int max = Integer.parseInt(split[1]);
//            int a = (int) Math.round(Math.random() * (max - min) + min);
//            int b = (int) Math.round(Math.random() * (max - min) + min);
//        }
    }

    private static boolean conditionsSatisfied() {
        return false;
    }


    private static String getLvlModifier(int lvl) {
        if (lvl == 3) {
            return "100-999";
        } else {
            return "";
        }
    }


}