package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookOpened extends AbstractEvent {

    private Long id;
    private Integer view;

    public BookOpened(Book aggregate) {
        super(aggregate);
    }

    public BookOpened() {
        super();
    }
}
//>>> DDD / Domain Event
