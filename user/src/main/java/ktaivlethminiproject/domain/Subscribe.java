package ktaivlethminiproject.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktaivlethminiproject.UserApplication;
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

    private Date expiredAt;

    public static SubscribeRepository repository() {
        SubscribeRepository subscribeRepository = UserApplication.applicationContext.getBean(
            SubscribeRepository.class
        );
        return subscribeRepository;
    }

    //<<< Clean Arch / Port Method
    public void borrowBook() {
        //implement business logic here:

        SubscriptionAccepted subscriptionAccepted = new SubscriptionAccepted(
            this
        );
        subscriptionAccepted.publishAfterCommit();
        SubscriptionDenied subscriptionDenied = new SubscriptionDenied(this);
        subscriptionDenied.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void ownBook() {
        //implement business logic here:

        SubscriptionAccepted subscriptionAccepted = new SubscriptionAccepted(
            this
        );
        subscriptionAccepted.publishAfterCommit();
        SubscriptionDenied subscriptionDenied = new SubscriptionDenied(this);
        subscriptionDenied.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
