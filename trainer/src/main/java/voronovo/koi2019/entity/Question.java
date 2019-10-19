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
    private String correctAnswer;
    private String image;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    private String winner;

    @PrePersist
    private void setCreationDate() {
        this.createdAt = this.createdAt != null ? this.createdAt : new Date();
    }
}
