package ktaivlethminiproject.domain;

import java.time.LocalDateTime;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
public class Published extends AbstractEvent {

    private Long bookId;
    private String summary;
    private String coverUrl;
    private LocalDateTime publishedAt;    

    public Published(Book aggregate) {
        super();

        this.bookId = aggregate.getBookId();
        this.summary = aggregate.getSummary();
        this.coverUrl = aggregate.getCoverUrl();
        this.publishedAt = aggregate.getPublishedAt();
    }
}