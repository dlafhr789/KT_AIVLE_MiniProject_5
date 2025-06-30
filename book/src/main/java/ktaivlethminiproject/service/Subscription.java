package ktaivlethminiproject.service;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 외부 Subscription 서비스로부터 받아올 구독 정보 DTO
 * - DB에 저장되지 않는, 순수한 데이터 전달용 클래스입니다.
 */
@Getter
@ToString
@NoArgsConstructor // JSON을 객체로 변환하기 위해 기본 생성자가 필요합니다.
public class Subscription {

    // 외부 서비스의 API 응답 필드와 이름/타입을 일치시켜야 합니다.
    private Long subscriptionId;
    private Long bookId;
    private Long userId;
    private String userName;
    private LocalDateTime subscribedAt;
}