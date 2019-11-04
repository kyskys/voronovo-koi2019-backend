package voronovo.koi2019.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@NamedEntityGraph(
        name = "TestScore.testItem",
        attributeNodes = @NamedAttributeNode("testItem")
)
public class TestScore {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private TestItem testItem;
    private String answer;

    public TestScore(String name, TestItem testItem, String answer) {
        this.name = name;
        this.testItem = testItem;
        this.answer = answer;
    }

    @JsonProperty("question")
    private String getQuestion() {
        return this.testItem.getCorrectAnswer();
    }
}
