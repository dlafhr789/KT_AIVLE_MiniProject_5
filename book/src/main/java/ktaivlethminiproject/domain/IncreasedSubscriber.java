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

    private Long bookId;
//    private Long userId;
    private Integer subscribers;

    public IncreasedSubscriber(Book aggregate) {
        super();
        this.bookId = aggregate.getBookId();
//        this.userId = aggregate.getUserId();
        this.subscribers = aggregate.getSubscribers();
    }
}
