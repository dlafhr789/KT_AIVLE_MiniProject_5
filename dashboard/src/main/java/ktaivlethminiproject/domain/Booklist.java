package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "Booklist_table")
@Data
public class Booklist {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String title;
    private String author;
    private Date publishedAt;
    private Integer view;
    private String coverUrl;
    private String summary;
    private String category;
    private Integer point;
    private String content;
    private String downloadUrl;
    private Integer subscribers;
}
