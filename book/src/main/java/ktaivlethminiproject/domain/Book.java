package ktaivlethminiproject.domain;

//import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
import javax.persistence.*;
//import ktaivlethminiproject.BookApplication;
//import ktaivlethminiproject.domain.BookOpenFailed;
//import ktaivlethminiproject.domain.BookSaved;
//import ktaivlethminiproject.domain.IncreasedSubscriber;
//import ktaivlethminiproject.domain.PublicationRequested;
//import ktaivlethminiproject.domain.Published;
import lombok.*;

@Entity
@Table(name = "Book_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private String imageUrl;

    public void openBook() {
        this.view++;

        BookOpened bookOpened = new BookOpened(this);
        bookOpened.publishAfterCommit();
    }

    public void publish(String imageUrl) {
        this.imageUrl = imageUrl;
        this.state = true;
        this.publishedAt = LocalDateTime.now();

        Published published = new Published(this);
        published.publishAfterCommit();
    }

    public void subscribed() {
        this.subscribers++;

        IncreasedSubscriber increasedSubscriber = new IncreasedSubscriber(this);
        increasedSubscriber.publishAfterCommit();
    }

    public void requestPublication() {
        PublicationRequested publicationRequested = new PublicationRequested(this);
        publicationRequested.publishAfterCommit();
    }

    @PostPersist
    public void onPostPersist() {
        BookSaved bookSaved = new BookSaved(this);
        bookSaved.publishAfterCommit();
    }
}
