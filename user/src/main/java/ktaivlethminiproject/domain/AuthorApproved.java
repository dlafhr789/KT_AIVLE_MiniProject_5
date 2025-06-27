package ktaivlethminiproject.domain;

import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class AuthorApproved extends AbstractEvent {

    private String userId;
}
