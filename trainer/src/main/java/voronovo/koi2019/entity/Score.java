package voronovo.koi2019.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private long time;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String name;
    private String category;
    private Integer percent;

    @PrePersist
    @PreUpdate
    private void setCreationDate() {
        this.date = this.date != null ? this.date : new Date();
    }
}
