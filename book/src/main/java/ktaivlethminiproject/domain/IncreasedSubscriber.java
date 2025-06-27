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
        this.id = aggregate.getId();
        this.userId = aggregate.getUserId();
        this.title = aggregate.getTitle();
        this.content = aggregate.getContent();
        this.publishedAt = aggregate.getPublishedAt();
        this.view = (aggregate.getView() == null) ? 0 : aggregate.getView();
        this.subscribers = (aggregate.getSubscribers() == null) ? 0 : aggregate.getSubscribers();
    }

    public IncreasedSubscriber() {
        super();
    }
}
//>>> DDD / Domain Event
