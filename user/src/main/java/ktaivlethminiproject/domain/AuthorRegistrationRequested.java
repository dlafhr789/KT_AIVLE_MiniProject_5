package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class AuthorRegistrationRequested extends AbstractEvent {

    private Long id;

    public AuthorRegistrationRequested() {
        super();
    }
}
//>>> DDD / Domain Event
