package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class PlanPurchased extends AbstractEvent {

    private Long id;
    private String plan;

    public PlanPurchased(User aggregate) {
        super(aggregate);
    }

    public PlanPurchased() {
        super();
    }
}
//>>> DDD / Domain Event
