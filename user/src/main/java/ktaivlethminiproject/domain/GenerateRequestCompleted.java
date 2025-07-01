package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.Data;

@Data
public class GenerateRequestCompleted extends AbstractEvent {

    private Long book_id;
    private String cover_url;
    private String summary;
    private String download_url;
    private Integer point;
    private Integer category;
    private Boolean request_successful;
}
