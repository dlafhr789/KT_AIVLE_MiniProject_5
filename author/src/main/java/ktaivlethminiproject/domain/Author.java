package ktaivlethminiproject.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktaivlethminiproject.AuthorApplication;
import ktaivlethminiproject.domain.AuthorRegistrationRequested;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@Table(name = "Author_table")
@Data
//<<< DDD / Aggregate Root
public class Author {

    @Id
    private String userId;

    @Enumerated(EnumType.STRING)
    private AuthorState state = AuthorState.PENDING;
    //private String state;

    private String portfolio;

    private String profile;

    // 작가 등록 요청
    @PostPersist
    public void onPostPersist() {
        AuthorRegistrationRequested authorRegistrationRequested = new AuthorRegistrationRequested(
            this
        );
        authorRegistrationRequested.publishAfterCommit();
    }

    public static AuthorRepository repository() {
        AuthorRepository authorRepository = AuthorApplication.applicationContext.getBean(
            AuthorRepository.class
        );
        return authorRepository;
    }

    // 작가 승인 (-> AuthorApproved 이벤트 발행)
    //<<< Clean Arch / Port Method
    public void authorApprove(AuthorApproveCommand authorApproveCommand) {
        //implement business logic here:
        if (state != AuthorState.PENDING) throw new IllegalStateException();
        state = AuthorState.APPROVED;

        AuthorApproved authorApproved = new AuthorApproved(this);
        authorApproved.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

    // 작가 승인 거부 (-> AuthorDenied 이벤트 발행)
    //<<< Clean Arch / Port Method
    public void authorDeny(AuthorDenyCommand authorDenyCommand) {
        //implement business logic here:
        if (state != AuthorState.PENDING) {
            throw new IllegalStateException("이미 승인/거절된 요청입니다.");
        }

        this.state = AuthorState.DENIED;   

        AuthorDenied authorDenied = new AuthorDenied(this);
        authorDenied.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
