package ktaivlethminiproject.domain;

import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GenerateRequestCompleted extends AbstractEvent {

    private Long id;
    private String summary;
    private String imageUrl;
//    private String downloadUrl;
//    private Integer point;
//    private Integer category;
//    private Boolean requestSuccessful;
}
