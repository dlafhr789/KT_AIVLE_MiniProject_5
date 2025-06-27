package ktaivlethminiproject.domain;

import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class GenerateRequestCompleted extends AbstractEvent {

    private Long bookId;
    private String coverUrl;
    private String summary;
    private String downloadUrl;
    private Integer point;
    private Integer category;
    private Boolean requestSuccessful;
}
