package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class AuthorApproved extends AbstractEvent {

    private Long id;

    public AuthorApproved() {
        super();
    }
}
//>>> DDD / Domain Event
