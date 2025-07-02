package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "SubscribeMonitor_table")
@Data
public class SubscribeMonitor {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Long userId;
    private String userName;
    private Long bookId;
    private String bookTitle;
    private String state;
    private Date expiredAt;
}
