package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.Data;

@Data
public class SubscriptionAccepted extends AbstractEvent {

    private Long id;
    private Long book_id;
    private Long user_id;
    private String user_name;
    private String state;
    private Date expired_at;
}
