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

    private Long id;
    private LocalDateTime publishedAt;
    private String coverUrl;
//    private String title;
//    private String content;
//    private Long userId;
//    private Boolean state;
//    private Integer view;

    public Published(Book aggregate) {
        super();

        this.id = aggregate.getId();
        this.publishedAt = aggregate.getPublishedAt();
        this.coverUrl = aggregate.getCoverUrl();
//        this.title = aggregate.getTitle();
//        this.content = aggregate.getContent();
//        this.userId = aggregate.getUserId();
//        this.state = aggregate.getState();
//        this.view = aggregate.getView();
    }
}
