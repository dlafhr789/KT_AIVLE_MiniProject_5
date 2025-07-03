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

        // 3. role 설정
        if (signUpCommand.getRole() != null) {
            this.role = signUpCommand.getRole();
        }

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
        // 추가부분
        planPurchased.setPlan(this.plan);
        planPurchased.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void decreasePoint(SubscriptionAccepted SubscriptionAccepted) {
        //implement business logic here:

        /** Example 1:  new item 
        User user = new User();
        repository().save(user);

        */
        if (SubscriptionAccepted.getUserId() == null) {
            throw new RuntimeException("잘못된 유저 접근입니다.");
        }
        repository().findById(SubscriptionAccepted.getUserId()).ifPresent(user -> {
        Integer currentPoint = user.getPoint() != null ? user.getPoint() : 0;
        Integer toDeduct = SubscriptionAccepted.getPointToDeduct() != null ? SubscriptionAccepted.getPointToDeduct() : 0;

        user.setPoint(currentPoint - toDeduct);
        repository().save(user);
        });
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateRole(AuthorApproved authorApproved) {
        Long userId = authorApproved.getUserId(); 

        if (userId == null) {
            throw new RuntimeException("잘못된 유저 접근입니다.");
        }

        repository().findById(userId).ifPresent(user -> {
            user.setRole("author");
            repository().save(user);
            System.out.println(user.getName() + "님의 작가 등록이 승인 되었습니다 ");
        });
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
