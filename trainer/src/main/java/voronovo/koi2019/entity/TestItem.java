package voronovo.koi2019.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class TestItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String expression;
    @ElementCollection
    @CollectionTable(name = "test_answer", joinColumns = @JoinColumn(name = "question_id"))
    private List<String> allOptions;
    private String correctAnswer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Test test;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<TestScore> scores;

    public TestItem(String expression, List<String> allOptions, String correctAnswer) {
        this.expression = expression;
        this.allOptions = allOptions;
        this.correctAnswer = correctAnswer;
    }

    public TestItem(Long id) {
        this.id = id;
    }
}
