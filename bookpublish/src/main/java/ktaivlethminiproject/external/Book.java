package ktaivlethminiproject.external;

import java.util.Date;
import lombok.Data;

@Data
public class Book {

    private Long bookId;
    private String title;
    private String content;
    private Long userId;
    private Date publishedAt;
    private String state;
    private Integer view;
    private Integer subscribers;
}
