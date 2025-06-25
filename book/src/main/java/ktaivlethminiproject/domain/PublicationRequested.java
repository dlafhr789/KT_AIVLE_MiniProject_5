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

    public PublicationRequested() {
        super();
    }
}
//>>> DDD / Domain Event
