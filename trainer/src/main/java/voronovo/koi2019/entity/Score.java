package voronovo.koi2019.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
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

    public Score(String name, String category, long time, Integer percent) {
        this.id = 0L;
        this.date = new Date();
        this.name = name;
        this.category = category;
        this.time = time;
        this.percent = percent;
    }

    @PrePersist
    @PreUpdate
    private void setCreationDate() {
        this.date = this.date != null ? this.date : new Date();
    }
}
