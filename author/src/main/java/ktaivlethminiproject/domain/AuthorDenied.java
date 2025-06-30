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
public class AuthorDenied extends AbstractEvent {

    private Long userId;
    private AuthorState state;

    public AuthorDenied(Author aggregate) {
        super(aggregate);
        this.userId = aggregate.getUserId();
        this.state = AuthorState.DENIED;
    }

    public AuthorDenied() {
        super();
    }
}
//>>> DDD / Domain Event
