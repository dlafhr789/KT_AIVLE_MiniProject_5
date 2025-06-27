package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class SubscriptionAccepted extends AbstractEvent {

    private Long id;
    private Long bookId;
    private Long userId;
    private String userName;

    public SubscriptionAccepted(Subscribe aggregate) {
        super(aggregate);
    }

    public SubscriptionAccepted() {
        super();
    }
}
//>>> DDD / Domain Event
