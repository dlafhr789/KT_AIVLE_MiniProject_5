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

    private String telecom;

    private String password;

    public static UserRepository repository() {
        UserRepository userRepository = UserApplication.applicationContext.getBean(
            UserRepository.class
        );
        return userRepository;
    }

    //<<< Clean Arch / Port Method
    public void signUp(SignUpCommand signUpCommand) {
        // 1. 기본 포인트 지급
        this.point = 1000;

        // 2. telecom이 "KT"면 추가 포인트 지급
        if ("KT".equalsIgnoreCase(signUpCommand.getTelecom())) {
            this.point += 5000;
        }

        this.name = signUpCommand.getName();
        this.email = signUpCommand.getEmail();
        this.telecom = signUpCommand.getTelecom();
        this.password = signUpCommand.getPassword();

        User.repository().save(this);
        
        SignedUp signedUp = new SignedUp(this);
        signedUp.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void planCancel() {
        //implement business logic here:

        this.plan = null;

        PlanCanceled planCanceled = new PlanCanceled(this);
        planCanceled.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void planPurchase(PlanPurchaseCommand planPurchaseCommand) {
        //implement business logic here:

        this.plan = planPurchaseCommand.getPlan();

        PlanPurchased planPurchased = new PlanPurchased(this);
        planPurchased.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void decreasePoint(
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
