package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
@Getter
public class AuthorApproved extends AbstractEvent {

    private Long userId;
    private AuthorState state;

    public AuthorApproved(Author aggregate) {
        super(aggregate);
        this.userId = aggregate.getUserId();
        this.state = aggregate.getState();
    }

    public AuthorApproved() {
        super();
    }
}
//>>> DDD / Domain Event
