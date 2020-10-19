package voronovo.koi2019.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private TestItem testItem;
    private String answer;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public TestScore(String name, TestItem testItem, String answer) {
        this.name = name;
        this.testItem = testItem;
        this.answer = answer;
    }

    @PrePersist
    private void prePersist() {
        this.createDate = new Date();
    }

    @JsonProperty("correctAnswer")
    private String correctAnswer() {
        return this.testItem.getCorrectAnswer();
    }

    @JsonProperty("expression")
    private String getExpression() {
        return this.testItem.getExpression();
    }
}
