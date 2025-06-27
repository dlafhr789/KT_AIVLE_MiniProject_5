package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class GenerateFailed extends AbstractEvent {

    private Long bookId;

    public GenerateFailed(GenData aggregate) {
        super(aggregate);
    }

    public GenerateFailed() {
        super();
    }
}
//>>> DDD / Domain Event
