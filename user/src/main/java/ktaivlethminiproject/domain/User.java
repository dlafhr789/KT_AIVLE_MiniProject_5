package ktaivlethminiproject.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktaivlethminiproject.UserApplication;
import ktaivlethminiproject.domain.PlanCanceled;
import ktaivlethminiproject.domain.PlanPurchased;
import ktaivlethminiproject.domain.SignedUp;
import lombok.Data;

@Entity
@Table(name = "User_table")
@Data
//<<< DDD / Aggregate Root
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String role;

    private String name;

    private String email;

    private Integer point;

    private String plan;

    private String state;

    @PostPersist
    public void onPostPersist() {
        PlanPurchased planPurchased = new PlanPurchased(this);
        planPurchased.publishAfterCommit();

        SignedUp signedUp = new SignedUp(this);
        signedUp.publishAfterCommit();

        PlanCanceled planCanceled = new PlanCanceled(this);
        planCanceled.publishAfterCommit();
    }

    public static UserRepository repository() {
        UserRepository userRepository = UserApplication.applicationContext.getBean(
            UserRepository.class
        );
        return userRepository;
    }

    //<<< Clean Arch / Port Method
    public static void pointsDecrease(
        SubscriptionAccepted subscriptionAccepted
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        User user = new User();
        repository().save(user);

        */

        /** Example 2:  finding and process
        

        repository().findById(subscriptionAccepted.get???()).ifPresent(user->{
            
            user // do something
            repository().save(user);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateRole(AuthorApproved authorApproved) {
        //implement business logic here:

        /** Example 1:  new item 
        User user = new User();
        repository().save(user);

        */

        /** Example 2:  finding and process
        

        repository().findById(authorApproved.get???()).ifPresent(user->{
            
            user // do something
            repository().save(user);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
