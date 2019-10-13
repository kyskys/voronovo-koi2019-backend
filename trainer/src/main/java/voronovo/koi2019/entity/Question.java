package voronovo.koi2019.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String description;
    @ElementCollection
    @CollectionTable(name = "question_answer", joinColumns = @JoinColumn(name = "question_id"))
    private List<String> allOptions;
    private String correctAnswer;
    private String image;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    private void setCreationDate() {
        this.createdAt = this.createdAt != null ? this.createdAt : new Date();
    }
}
