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
    private String state;
    private Date expiredAt;

    private Integer pointToDeduct;

    public SubscriptionAccepted(Subscribe aggregate) {
        super(aggregate);
        this.pointToDeduct = pointToDeduct;
    }

    public SubscriptionAccepted() {
        super();
    }
}
//>>> DDD / Domain Event
