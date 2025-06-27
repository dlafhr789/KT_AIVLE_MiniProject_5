package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class SubscriptionDenied extends AbstractEvent {

    private Long id;
    private Long bookId;
    private Long userId;

    public SubscriptionDenied(Subscribe aggregate) {
        super(aggregate);
    }

    public SubscriptionDenied() {
        super();
    }
}
//>>> DDD / Domain Event
