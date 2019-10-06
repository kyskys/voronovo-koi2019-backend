package voronovo.koi2019.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String expression;
    @ElementCollection
    @CollectionTable(name = "test_answer", joinColumns = @JoinColumn(name = "question_id"))
    private List<String> allOptions;
    private String correctAnswer;

    public Test(String expression, List<String> allOptions, String correctAnswer) {
        this.expression = expression;
        this.allOptions = allOptions;
        this.correctAnswer = correctAnswer;
    }
}
