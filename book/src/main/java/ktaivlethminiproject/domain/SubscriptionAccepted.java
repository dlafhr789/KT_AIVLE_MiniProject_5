package ktaivlethminiproject.domain;

import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class SubscriptionAccepted extends AbstractEvent {

    private Long id;
    private Long bookId;
    private Long userId;
    private String userName;
    private String state;
    private Date expiredAt;
}
