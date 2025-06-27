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
        //implement business logic here:

        BookOpened bookOpened = new BookOpened(this);
        bookOpened.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void publish(
        GenerateRequestCompleted generateRequestCompleted
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Book book = new Book();
        repository().save(book);

        Published published = new Published(book);
        published.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        // if generateRequestCompleted.genAiId exists, use it
        
        // ObjectMapper mapper = new ObjectMapper();
        // Map<, Object> genDataMap = mapper.convertValue(generateRequestCompleted.getGenAiId(), Map.class);

        repository().findById(generateRequestCompleted.get???()).ifPresent(book->{
            
            book // do something
            repository().save(book);

            Published published = new Published(book);
            published.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void subscribed(SubscriptionAccepted subscriptionAccepted) {
        //implement business logic here:

        /** Example 1:  new item 
        Book book = new Book();
        repository().save(book);

        IncreasedSubscriber increasedSubscriber = new IncreasedSubscriber(book);
        increasedSubscriber.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(subscriptionAccepted.get???()).ifPresent(book->{
            
            book // do something
            repository().save(book);

            IncreasedSubscriber increasedSubscriber = new IncreasedSubscriber(book);
            increasedSubscriber.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
