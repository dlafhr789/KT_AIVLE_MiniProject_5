package ktaivlethminiproject.domain;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktaivlethminiproject.UserApplication;
import ktaivlethminiproject.infra.BookPointRepository;
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

    public static BookPointRepository bookPointRepository() {
        return UserApplication.applicationContext.getBean(BookPointRepository.class);
    }

    //<<< Clean Arch / Port Method
    public void borrowBook() {
        //implement business logic here:
        User user = User.repository().findById(this.userId).orElseThrow(() -> new RuntimeException("User not found"));
        
        if (user.getPlan() == null) {
            this.state = "거부";
            SubscriptionDenied denied = new SubscriptionDenied(this);
            denied.publishAfterCommit();
            
            return;
        }

        this.state = "대여";
        this.expiredAt = Date.from(Instant.now().plus(7, ChronoUnit.DAYS));

        SubscriptionAccepted accepted = new SubscriptionAccepted(this);
        accepted.setPointToDeduct(0);
        accepted.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void ownBook() {
        //implement business logic here:
        User user = User.repository().findById(this.userId).orElseThrow(() -> new RuntimeException("User not found"));
        // 책 포인트를 어떻게 가져옴? 새로운 읽기모델?
        BookPoint bookPoint = bookPointRepository().findById(bookId).orElseThrow(() -> new RuntimeException("BookPoint not found"));
        
        int userPoint = user.getPoint() != null ? user.getPoint() : 0;
        int bookPointValue = bookPoint.getPoint() != null ? bookPoint.getPoint() : 0;

        if (userPoint < bookPointValue) {
            this.state = "거부";
            SubscriptionDenied denied = new SubscriptionDenied(this);
            denied.publishAfterCommit();
            return;
        }

        this.state = "소장"; 
        this.expiredAt = null;

        SubscriptionAccepted accepted = new SubscriptionAccepted(this);
        accepted.setPointToDeduct(bookPointValue);
        accepted.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
