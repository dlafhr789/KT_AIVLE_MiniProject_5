package ktaivlethminiproject.domain;

// 작가(Account) 상태 값
public enum AuthorState {
    PENDING,   // 등록 요청 완료–승인 대기
    APPROVED,  // 승인 완료
    DENIED     // 승인 거절
}
