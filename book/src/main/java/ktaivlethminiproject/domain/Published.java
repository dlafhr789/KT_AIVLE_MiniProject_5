package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Published extends AbstractEvent {

    private Long id;
    private String title;
    private String content;
    private Long userId;
    private String state;
    private Integer view;
    private Date publishedAt;
    private String imageUrl;  // 임로기가 줄 이미지 url~~~~


    public Published(Book aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.title = aggregate.getTitle();
        this.content = aggregate.getContent();
        this.userId = aggregate.getUserId();
        this.state = aggregate.getState();
        this.view = (aggregate.getView() == null) ? 0 : aggregate.getView();
        this.publishedAt = aggregate.getPublishedAt();
        this.imageUrl = aggregate.getImageUrl();  // Book 엔티티의 imageUrl 사용
    }

    public Published() {
        super();
    }
}
//>>> DDD / Domain Event
