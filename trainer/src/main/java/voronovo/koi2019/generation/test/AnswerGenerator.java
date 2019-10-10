package voronovo.koi2019.generation.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import voronovo.koi2019.generation.type.AnswerGeneratorType;

@Data
@AllArgsConstructor
public class AnswerGenerator {
    private AnswerGeneratorType type;
    private int frequency;
    private String value;
}
