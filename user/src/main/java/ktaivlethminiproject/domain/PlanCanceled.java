package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class PlanCanceled extends AbstractEvent {

    private Long id;
    private String plan;

    public PlanCanceled(User aggregate) {
        super(aggregate);
    }

    public PlanCanceled() {
        super();
    }
}
//>>> DDD / Domain Event
