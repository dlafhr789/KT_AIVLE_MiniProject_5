package ktaivlethminiproject.view;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name="BookList_table")
@Data
public class BookList {

    @Id
    private Long id;
    private String title;
    private Integer view;
    private Integer subscribers;
    private String userId;
    private String imageUrl;
    private String summary;
}