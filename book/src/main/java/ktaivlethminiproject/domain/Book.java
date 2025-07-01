package ktaivlethminiproject.domain;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;
import java.util.List;
import java.util.ArrayList;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//import ktaivlethminiproject.BookApplication;
//import ktaivlethminiproject.domain.BookOpenFailed;
//import ktaivlethminiproject.domain.BookSaved;
//import ktaivlethminiproject.domain.IncreasedSubscriber;
//import ktaivlethminiproject.domain.PublicationRequested;
//import ktaivlethminiproject.domain.Published;

@Entity
@Table(name = "Book_table")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String content;
    private Long userId;
    private LocalDateTime publishedAt;
    private Boolean state = false;
    private Integer view = 0;
    private Integer subscribers = 0;
    private String summary;
    private String imageUrl;

    @ElementCollection
    @CollectionTable(name="book_subscribers", joinColumns=@JoinColumn(name="book_id"))
    @Column(name="user_id")
    private List<Long> subscriberIds = new ArrayList<>();

    @PostPersist
    public void onPostPersist() {
        BookSaved bookSaved = new BookSaved(this);
        bookSaved.publishAfterCommit();
    }

    public void openBook() {
        this.view++;
        BookOpened bookOpened = new BookOpened(this);
        bookOpened.publishAfterCommit();
    }

    public void publish(String imageUrl, String summary) {
        this.summary = summary;
        this.imageUrl = imageUrl;
        this.state = true;
        this.publishedAt = LocalDateTime.now();
        Published published = new Published(this);
        published.publishAfterCommit();
    }

    public void subscribed(Long userId) {
        this.subscribers++;

        this.subscriberIds.add(userId);

        IncreasedSubscriber increasedSubscriber = new IncreasedSubscriber(this);
        increasedSubscriber.publishAfterCommit();
    }

    public void requestPublication() {
        PublicationRequested publicationRequested = new PublicationRequested(this);
        publicationRequested.publishAfterCommit();
    }
}
