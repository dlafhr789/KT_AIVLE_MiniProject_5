package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class IncreasedSubscriber extends AbstractEvent {

    private Long id;
    private String title;
    private String content;
    private Long userId;
    private Date publishedAt;
    private Integer view;
    private Integer subscribers;

    public IncreasedSubscriber(Book aggregate) {
        super(aggregate);
    }

    public IncreasedSubscriber() {
        super();
    }
}
//>>> DDD / Domain Event
