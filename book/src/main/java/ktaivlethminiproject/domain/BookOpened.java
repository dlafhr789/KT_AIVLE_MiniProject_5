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
public class BookOpened extends AbstractEvent {
    private Long bookId;
    private Integer view;

    public BookOpened(Book aggregate) {
        super();
        this.bookId = aggregate.getBookId();
        this.view = aggregate.getView();
    }
}
