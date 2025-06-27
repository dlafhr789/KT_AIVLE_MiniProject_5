package ktaivlethminiproject.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktaivlethminiproject.AuthorApplication;
import ktaivlethminiproject.domain.AuthorApproved;
import ktaivlethminiproject.domain.AuthorDenied;
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

        AuthorApproved authorApproved = new AuthorApproved(this);
        authorApproved.publishAfterCommit();

        AuthorDenied authorDenied = new AuthorDenied(this);
        authorDenied.publishAfterCommit();
    }

    public static AuthorRepository repository() {
        AuthorRepository authorRepository = AuthorApplication.applicationContext.getBean(
            AuthorRepository.class
        );
        return authorRepository;
    }
}
//>>> DDD / Aggregate Root
