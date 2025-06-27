package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class PublicationRequested extends AbstractEvent {

    private Long id;
    private String title;
    private String content;
    private Long userId;

    public PublicationRequested(Book aggregate) {
        super(aggregate);
    }

    public PublicationRequested() {
        super();
    }
}
//>>> DDD / Domain Event
