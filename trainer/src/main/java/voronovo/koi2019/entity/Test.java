package voronovo.koi2019.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Test {
    @Id
    private Long id;
    private String name;
    private String value;
    private String[] answers;
    private String correctAnswer;
}
