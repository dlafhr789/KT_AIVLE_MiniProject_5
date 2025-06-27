package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Published extends AbstractEvent {

    private Long id;
    private String title;
    private String content;
    private Long userId;
    private String state;
    private Integer view;
    private Date publishedAt;

    public Published(Book aggregate) {
        super(aggregate);
    }

    public Published() {
        super();
    }
}
//>>> DDD / Domain Event
