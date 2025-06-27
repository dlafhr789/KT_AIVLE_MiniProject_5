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

@Entity
@Table(name = "Author_table")
@Data
//<<< DDD / Aggregate Root
public class Author {

    @Id
    private String userId;

    private String state;

    private String portfolio;

    private String profile;

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

    //<<< Clean Arch / Port Method
    public void authorApprove(AuthorApproveCommand authorApproveCommand) {
        //implement business logic here:

        AuthorApproved authorApproved = new AuthorApproved(this);
        authorApproved.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void authorDeny(AuthorDenyCommand authorDenyCommand) {
        //implement business logic here:

        AuthorDenied authorDenied = new AuthorDenied(this);
        authorDenied.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
