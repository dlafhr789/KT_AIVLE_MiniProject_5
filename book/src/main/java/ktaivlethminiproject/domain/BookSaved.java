package ktaivlethminiproject.domain;

import java.time.LocalDateTime;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookSaved extends AbstractEvent {

    private Long id;
    private Long userId;
    private String title;
    private String content;
    private Integer view;

    public BookSaved(Book aggregate) {
        super();
        this.id = aggregate.getId();
        this.userId = aggregate.getUserId();
        this.title = aggregate.getTitle();
        this.content = aggregate.getContent();
        this.view = aggregate.getView();
    }
}
