package ktaivlethminiproject.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
//import ktaivlethminiproject.BookApplication;
//import ktaivlethminiproject.domain.BookOpenFailed;
//import ktaivlethminiproject.domain.BookSaved;
//import ktaivlethminiproject.domain.IncreasedSubscriber;
//import ktaivlethminiproject.domain.PublicationRequested;
//import ktaivlethminiproject.domain.Published;
import lombok.Data;

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
    private String imageUrl;  // 임록님만 믿겠습니다 이미지  url 화이팅

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

    public void Subscribed() {
        this.subscribers++;

        // 실제 구독자 증가 행위가 일어났을 때 이벤트 발행
        IncreasedSubscriber increasedSubscriber = new IncreasedSubscriber(this);
        increasedSubscriber.publishAfterCommit();
    }

    public void requestPublication() {
        PublicationRequested publicationRequested = new PublicationRequested(this);
        publicationRequested.publishAfterCommit();
    }
}
