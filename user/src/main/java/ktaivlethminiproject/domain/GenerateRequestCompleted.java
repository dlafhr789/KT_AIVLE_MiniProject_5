package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.Data;

@Data
public class GenerateRequestCompleted extends AbstractEvent {

    private Long bookId;
    private String coverUrl;
    private String summary;
    private String downloadUrl;
    private Integer point;
    private Integer category;
    private Boolean requestSuccessful;
}
