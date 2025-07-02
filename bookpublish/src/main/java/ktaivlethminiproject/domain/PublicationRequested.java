package ktaivlethminiproject.domain;

import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class PublicationRequested extends AbstractEvent {

    private Long bookId;
    private String title;
    private String content;
    private Long userId;
}
