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
public class BookOpened extends AbstractEvent {
    private Long id;
    private Integer view;

    public BookOpened(Book aggregate) {
        super();
        this.id = aggregate.getId();
        this.view = aggregate.getView();
    }
}
