package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.Data;

@Data
public class SubscriptionAccepted extends AbstractEvent {

    private Long id;
    private Long bookId;
    private Long userId;
    private String userName;
    private String state;
    private Date expiredAt;
}
