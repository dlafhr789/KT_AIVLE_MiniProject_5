package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "BookPoint_table")
@Data
public class BookPoint {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Integer point;
}
