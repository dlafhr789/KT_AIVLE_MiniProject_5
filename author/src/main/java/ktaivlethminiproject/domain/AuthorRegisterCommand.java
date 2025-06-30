package ktaivlethminiproject.domain;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class AuthorRegisterCommand {

    @NotBlank
    private String userId;

    private String portfolio;
    private String profile;
}
