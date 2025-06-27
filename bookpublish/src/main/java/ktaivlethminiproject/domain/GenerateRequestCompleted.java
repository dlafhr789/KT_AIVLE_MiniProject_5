package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class GenerateRequestCompleted extends AbstractEvent {

    private Long bookId;
    private String coverUrl;
    private String summary;
    private String downloadUrl;
    private Integer point;
    private Integer category;
    private Boolean requestSuccessful;

    public GenerateRequestCompleted(GenData aggregate) {
        super(aggregate);
    }

    public GenerateRequestCompleted() {
        super();
    }
}
//>>> DDD / Domain Event
