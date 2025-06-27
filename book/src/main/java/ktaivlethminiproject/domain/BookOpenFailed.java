package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookOpenFailed extends AbstractEvent {

    private Long id;

    public BookOpenFailed(Book aggregate) {
        super(aggregate);
    }

    public BookOpenFailed() {
        super();
    }
}
//>>> DDD / Domain Event
