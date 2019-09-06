package voronovo.koi2019;

import voronovo.koi2019.api.AnswerGenerator;
import voronovo.koi2019.api.Calculator;
import voronovo.koi2019.api.SimpleIntegerResultCalculator;
import voronovo.koi2019.task.Task;
import voronovo.koi2019.task.TaskBuilder;

import java.util.Stack;

public class MathTaskMain {

//    public class VariableInitListener {
//        private voronovo.koi2019.task.TaskBuilder taskContext;
//        private String variableId;
//        private voronovo.koi2019.condition.Precondition precondition;
//
//        VariableInitListener create(String variableId, voronovo.koi2019.condition.Precondition precondition, voronovo.koi2019.task.TaskBuilder taskContext) {
//            return new VariableInitListener(variableId, precondition, taskContext);
//        }
//
//        private VariableInitListener(String variableId, voronovo.koi2019.condition.Precondition precondition, voronovo.koi2019.task.TaskBuilder taskContext) {
//            this.taskContext = taskContext;
//            this.precondition = precondition;
//            this.variableId = variableId;
//        }
//
//        public Optional<VariableInitListener> tryToCalculate() {
//            return null;
////            precondition.getPreconditionType().generateValue();
//        }
//    }

    public static void main(String[] args) {
//        voronovo.koi2019.util.RegExpUtil.findAll("dsnfkjsdnf kjnsd var skdf [var1] [var1]  [var222]", voronovo.koi2019.util.RegexConst.VARIABLE_REGEX).forEach(System.out::println);
//        voronovo.koi2019.util.RegExpUtil.findAllUnique("dsnfkjsdnf kjnsd var skdf [var1] [var1]  [var222]", voronovo.koi2019.util.RegexConst.VARIABLE_REGEX).forEach(System.out::println);
        TaskBuilder taskBuilder = new TaskBuilder("[var1] + [var2] * [var3] + [var444]", "var1 between 1;100 " +
                "<> var2 between 50;55 <> var3 between 3;33 <> var444 == 9", new SimpleIntegerResultCalculator(), new AnswerGenerator() {});
        //3 варианта неправильных ответов
        Task build = taskBuilder.build(3);
        System.out.println(build.toString());
    }
}
