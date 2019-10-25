package voronovo.koi2019.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startedAt;
    private boolean active;
    @OneToMany(mappedBy = "test")
    private List<TestItem> items;
}
