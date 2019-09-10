package voronovo.koi2019.generation.task;

import java.util.List;

public class Task {
    private String expression;
    private List<String> allOptions;
    private String correctAnswer;

    public Task(String expression, List<String> allOptions, String correctAnswer) {
        this.expression = expression;
        this.allOptions = allOptions;
        this.correctAnswer = correctAnswer;
    }

    public String getExpression() {
        return expression;
    }

    public List<String> getAllOptions() {
        return allOptions;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(expression).append("\n");
        sb.append("Options:").append("\n");
        for (int i = 0; i < allOptions.size(); i++) {
            sb.append(i+1).append(") ").append(allOptions.get(i)).append("\n");
        }
        sb.append("correct answer: ").append(correctAnswer).append("\n");
        return sb.toString();
    }
}
