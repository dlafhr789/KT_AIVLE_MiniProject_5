package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class AuthorDenyCommand {

    private String userId;
    private String state;
}
