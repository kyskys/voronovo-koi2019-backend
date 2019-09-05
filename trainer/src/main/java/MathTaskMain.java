

public class MathTaskMain {

//    public class VariableInitListener {
//        private TaskBuilder taskContext;
//        private String variableId;
//        private Precondition precondition;
//
//        VariableInitListener create(String variableId, Precondition precondition, TaskBuilder taskContext) {
//            return new VariableInitListener(variableId, precondition, taskContext);
//        }
//
//        private VariableInitListener(String variableId, Precondition precondition, TaskBuilder taskContext) {
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
//        RegExpUtil.findAll("dsnfkjsdnf kjnsd var skdf [var1] [var1]  [var222]", RegexConst.VARIABLE_REGEX).forEach(System.out::println);
//        RegExpUtil.findAllUnique("dsnfkjsdnf kjnsd var skdf [var1] [var1]  [var222]", RegexConst.VARIABLE_REGEX).forEach(System.out::println);
        TaskBuilder taskBuilder = new TaskBuilder("[var1] + [var2] + [var3] + [var444]", "var1 between 1;100 <> var2 between 50;55 <> var3 between 3;33 <> var444 == 9", new Calculator() {}, new AnswerGenerator() {});
        //3 варианта неправильных ответов
        Task build = taskBuilder.build(3);
        System.out.println(build.toString());
    }


}
