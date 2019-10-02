package voronovo.koi2019.generation.condition;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerGenerator {
    private AnswerGeneratorType type;
    private int frequency;
    private String value;
}
