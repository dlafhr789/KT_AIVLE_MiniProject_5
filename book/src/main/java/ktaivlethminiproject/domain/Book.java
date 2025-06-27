package ktaivlethminiproject.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktaivlethminiproject.BookApplication;
import ktaivlethminiproject.domain.BookOpenFailed;
import ktaivlethminiproject.domain.BookSaved;
import ktaivlethminiproject.domain.IncreasedSubscriber;
import ktaivlethminiproject.domain.PublicationRequested;
import ktaivlethminiproject.domain.Published;
import lombok.Data;

@Entity
@Table(name = "Book_table")
@Data
//<<< DDD / Aggregate Root
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String content;

    private Long userId;

    private Date publishedAt;

    private String state;

    private Integer view;

    private Integer subscribers;

    private String imageUrl;  // 임록님만 믿겠습니다 이미지  url 화이팅

    @PostPersist
    public void onPostPersist() {
        PublicationRequested publicationRequested = new PublicationRequested(
            this
        );
        publicationRequested.publishAfterCommit();

        BookSaved bookSaved = new BookSaved(this);
        bookSaved.publishAfterCommit();

        BookOpenFailed bookOpenFailed = new BookOpenFailed(this);
        bookOpenFailed.publishAfterCommit();

        Published published = new Published(this);
        published.publishAfterCommit();

        IncreasedSubscriber increasedSubscriber = new IncreasedSubscriber(this);
        increasedSubscriber.publishAfterCommit();
    }

    public static BookRepository repository() {
        BookRepository bookRepository = BookApplication.applicationContext.getBean(
            BookRepository.class
        );
        return bookRepository;
    }

    //<<< Clean Arch / Port Method
    public void openBook(OpenBookCommand openBookCommand) {
        // 1) view 증가
        this.view = (this.view == null) ? 0 : this.view;
        this.view++;
        repository().save(this);

        BookOpened bookOpened = new BookOpened(this);
        bookOpened.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void publish(
        GenerateRequestCompleted generateRequestCompleted
    ) {
         Book book = new Book();
        book.setTitle(generateRequestCompleted.getGeneratedTitle());
        book.setContent(generateRequestCompleted.getGeneratedContent());
        book.setUserId(generateRequestCompleted.getUserId());
        book.setState("PUBLISHED");
        book.setPublishedAt(new Date());
        book.setImageUrl(generateRequestCompleted.getImageUrl()); // URL 사용
        repository().save(book);

        Published pub = new Published(book);
        pub.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void subscribed(SubscriptionAccepted subscriptionAccepted) {
        repository().findById(subscriptionAccepted.getBookId()).ifPresent(book -> {
            book.setSubscribers(
                (book.getSubscribers() == null) ? 1 : book.getSubscribers() + 1
            );
            repository().save(book);

            IncreasedSubscriber inc = new IncreasedSubscriber(book);
            inc.publishAfterCommit();
        });
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
