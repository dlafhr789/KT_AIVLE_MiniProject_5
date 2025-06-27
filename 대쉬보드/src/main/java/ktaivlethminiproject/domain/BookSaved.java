package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.Data;

@Data
public class BookSaved extends AbstractEvent {

    private Long id;
    private Long user_id;
    private String title;
    private String content;
    private String view;
}
