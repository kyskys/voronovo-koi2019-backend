package voronovo.koi2019.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
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
    @DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @PrePersist
    private void setCreationDate() {
        this.createdAt = this.createdAt != null ? this.createdAt : new Date();
    }
}
