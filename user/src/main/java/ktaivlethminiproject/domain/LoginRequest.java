package ktaivlethminiproject.domain;   // ← 패키지는 프로젝트 구조에 맞게
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String email;
    private String password;
}