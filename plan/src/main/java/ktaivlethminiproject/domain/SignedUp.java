package ktaivlethminiproject.domain;

import java.util.*;
import ktaivlethminiproject.domain.*;
import ktaivlethminiproject.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class SignedUp extends AbstractEvent {

    private Long id;
}
