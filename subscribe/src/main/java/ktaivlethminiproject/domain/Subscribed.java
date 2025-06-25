package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Subscribed extends AbstractEvent {

    private Long id;

    public Subscribed() {
        super();
    }
}
//>>> DDD / Domain Event
