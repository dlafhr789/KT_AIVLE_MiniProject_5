package ktaivlethminiproject.domain;

import java.time.LocalDateTime;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class IncreasedSubscriber extends AbstractEvent {

    private Long id;
    private Long userId;
    private Integer subscribers;
//    private String title;
//    private String content;
//    private LocalDateTime publishedAt;
//    private Integer view;


    public IncreasedSubscriber(Book aggregate) {
        super();
        this.id = aggregate.getId();
        this.userId = aggregate.getUserId();
        this.subscribers = aggregate.getSubscribers();
//        this.title = aggregate.getTitle();
//        this.content = aggregate.getContent();
//        this.publishedAt = aggregate.getPublishedAt();
//        this.view = aggregate.getView();
    }
}
