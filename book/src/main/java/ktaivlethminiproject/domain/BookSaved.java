package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookSaved extends AbstractEvent {

    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String view;

    public BookSaved(Book aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.userId = aggregate.getUserId();
        this.title = aggregate.getTitle();
        this.content = aggregate.getContent();
        this.view = (aggregate.getView() == null) ? 0 : aggregate.getView();
    }

    public BookSaved() {
        super();
    }
}
//>>> DDD / Domain Event
