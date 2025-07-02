package ktaivlethminiproject.domain;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import lombok.*;

@Data
public class AuthorUpdateCommand {
    private String portfolio;
    private String profile;
}