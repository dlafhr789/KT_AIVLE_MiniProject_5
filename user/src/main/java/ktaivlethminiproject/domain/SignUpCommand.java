package ktaivlethminiproject.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class SignUpCommand {

    private String name;
    private String email;
    private String telecom;
    private String password;
}
