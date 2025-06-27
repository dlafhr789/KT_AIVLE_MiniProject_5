package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class GenerateSucceeded extends AbstractEvent {

    private Long bookId;
    private String coverUrl;
    private String summary;
    private String downloadUrl;
    private Integer point;
    private Integer category;

    public GenerateSucceeded(GenData aggregate) {
        super(aggregate);
    }

    public GenerateSucceeded() {
        super();
    }
}
//>>> DDD / Domain Event
