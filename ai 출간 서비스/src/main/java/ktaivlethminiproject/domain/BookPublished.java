package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookPublished extends AbstractEvent {

    private Long id;

    public BookPublished() {
        super();
    }
}
//>>> DDD / Domain Event
