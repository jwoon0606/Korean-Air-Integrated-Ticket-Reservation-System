package strategy;

/**
 * 인증 전략 인터페이스
 * 다양한 사용자 유형에 대한 인증 방식을 정의합니다.
 * 전략 패턴의 Strategy 역할을 합니다.
 */
public interface AuthenticationStrategy {
    
    /**
     * 사용자 로그인 인증을 수행합니다.
     * 
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     * @return 인증 성공 여부
     */
    boolean authenticate(String email, String password);
    
    /**
     * 새 사용자를 등록합니다.
     * 
     * @return 등록 성공 여부
     */
    boolean register();
    
    /**
     * 전략의 이름을 반환합니다.
     * 
     * @return 전략 이름 (예: "일반 사용자", "여행사")
     */
    String getStrategyName();
    
    /**
     * 로그인한 사용자 정보를 표시합니다.
     */
    void displayUserInfo();
    
    /**
     * 현재 로그인된 사용자를 로그아웃 처리합니다.
     */
    void logout();
    
    /**
     * 현재 로그인 상태를 확인합니다.
     * 
     * @return 로그인 상태
     */
    boolean isLoggedIn();
}