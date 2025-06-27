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
    }

    public BookSaved() {
        super();
    }
}
//>>> DDD / Domain Event
