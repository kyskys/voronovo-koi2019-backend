package voronovo.koi2019.entity;

import lombok.Data;

import javax.annotation.Generated;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Temporal(TemporalType.TIME)
    private Date time;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String name;
    private String category;

    @PrePersist
    private void setCreationDate() {
        this.date = this.date != null ? this.date : new Date();
    }
}
