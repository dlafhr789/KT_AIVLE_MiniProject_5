package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookOpened extends AbstractEvent {

    /**
     * 파라미터 있는 생성자에서
     * super(aggregate) 호출로 메타정보 초기화 후
     * id/view를 복사합니다.
     */
    public BookOpened(Book aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.view = aggregate.getView();
    }

    /**
     * 역직렬화 라이브러리를 위한 기본 생성자
     */
    public BookOpened() {
        super();
    }
}
//>>> DDD / Domain Event
