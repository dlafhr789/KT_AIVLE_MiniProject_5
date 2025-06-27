package ktaivlethminiproject.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktaivlethminiproject.UserApplication;
import ktaivlethminiproject.domain.SubscriptionAccepted;
import ktaivlethminiproject.domain.SubscriptionDenied;
import lombok.Data;

@Entity
@Table(name = "Subscribe_table")
@Data
//<<< DDD / Aggregate Root
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long bookId;

    private Long userId;

    private String state;

    @PostPersist
    public void onPostPersist() {
        SubscriptionAccepted subscriptionAccepted = new SubscriptionAccepted(
            this
        );
        subscriptionAccepted.publishAfterCommit();

        SubscriptionDenied subscriptionDenied = new SubscriptionDenied(this);
        subscriptionDenied.publishAfterCommit();
    }

    public static SubscribeRepository repository() {
        SubscribeRepository subscribeRepository = UserApplication.applicationContext.getBean(
            SubscribeRepository.class
        );
        return subscribeRepository;
    }
}
//>>> DDD / Aggregate Root
