import java.util.List;

public class Task {
    private String expresstion;
    private List<String> allOptions;
    private String correctAnswer;

    public Task(String expresstion, List<String> allOptions, String correctAnswer) {
        this.expresstion = expresstion;
        this.allOptions = allOptions;
        this.correctAnswer = correctAnswer;
    }

    public String getExpresstion() {
        return expresstion;
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
        sb.append(expresstion).append("\n");
        sb.append("Options:").append("\n");
        for (int i = 0; i < allOptions.size(); i++) {
            sb.append(i+1).append(") ").append(allOptions.get(i)).append("\n");
        }
        sb.append("correct answer: ").append(correctAnswer).append("\n");
        return sb.toString();
    }
}
