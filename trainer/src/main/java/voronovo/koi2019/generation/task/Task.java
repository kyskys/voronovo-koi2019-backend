package voronovo.koi2019.generation.task;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Task {
    private String expression;
    private List<String> allOptions;
    private String correctAnswer;

    public Task(String expression, List<String> allOptions, String correctAnswer) {
        this.expression = expression;
        this.allOptions = allOptions;
        this.correctAnswer = correctAnswer;
    }
}
