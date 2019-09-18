package voronovo.koi2019.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
public class Test {
    @Id
    private Long id;
    private String expression;
    @Type( type = "string-array" )
    private List<String> allOptions;
    private String correctAnswer;

    public Test(String expression, List<String> allOptions, String correctAnswer) {
        this.expression = expression;
        this.allOptions = allOptions;
        this.correctAnswer = correctAnswer;
    }
}
