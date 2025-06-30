package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PublicationRequested extends AbstractEvent {

    private Long id;
    private String title;
    private String content;
    private Long userId;

    public PublicationRequested(Book aggregate) {
        super();
        this.id = aggregate.getId();
        this.title = aggregate.getTitle();
        this.content = aggregate.getContent();
        this.userId = aggregate.getUserId();
    }
}
