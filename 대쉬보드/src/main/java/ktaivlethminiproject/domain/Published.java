package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.Data;

@Data
public class Published extends AbstractEvent {

    private Long id;
    private String title;
    private String content;
    private Long user_id;
    private String state;
    private Integer view;
    private Date published_at;
}
