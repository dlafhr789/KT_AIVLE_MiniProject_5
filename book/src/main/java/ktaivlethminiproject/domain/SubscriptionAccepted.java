package ktaivlethminiproject.domain;

import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;
//import java.time.LocalDateTime

//@Data
//@ToString
//public class SubscriptionAccepted extends AbstractEvent {
//
//    private Long id;
//    private Long bookId;
//    private Long userId;
//    private String userName;
//    private String state;
//    private Date expiredAt;
//}

// 구독됨 정책
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionAccepted {
    private Long bookId;
    private Long userId;
    private Boolean state;
}