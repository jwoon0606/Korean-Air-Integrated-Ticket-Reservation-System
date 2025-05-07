package controller;
import strategy.AuthenticationStrategy;
import user.RegisteredPassenger;
import user.TravelAgency;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/** 
* 전략 패턴을 적용한 로그인 컨트롤러
* Context 역할을 수행하여 다양한 인증 전략을 관리합니다.
*/
public class LoginController {
    private final Scanner sc = new Scanner(System.in);
    
    // 전략 패턴: 인증 전략들을 저장하는 맵
    private Map<String, AuthenticationStrategy> strategies;
    
    // 현재 선택된 인증 전략
    private AuthenticationStrategy currentStrategy;
    
    /**
     * 생성자: 사용 가능한 인증 전략들을 초기화합니다.
     * 의존성 주입을 통해 전략들을 받습니다.
     * 
     * @param userStrategy 일반 사용자 인증 전략
     * @param agencyStrategy 여행사 인증 전략
     */
    public LoginController(AuthenticationStrategy userStrategy, AuthenticationStrategy agencyStrategy) {
        strategies = new HashMap<>();
        
        // 인증 전략 등록
        strategies.put("user", userStrategy);
        strategies.put("agency", agencyStrategy);
        
        // 기본값으로 일반 사용자 인증 전략 설정
        currentStrategy = strategies.get("user");
    }
    
    /**
     * 일반 사용자 로그인
     */
    public void login() {
        // 일반 사용자 인증 전략으로 변경
        currentStrategy = strategies.get("user");
        
        System.out.print("email? ");
        String email = sc.nextLine();
        System.out.print("password? ");
        String password = sc.nextLine();

        currentStrategy.authenticate(email, password);
    }
    
    /**
     * 일반 사용자 회원가입
     */
    public void signUp() {
        // 일반 사용자 인증 전략으로 변경
        currentStrategy = strategies.get("user");
        currentStrategy.register();
    }

    /**
     * 여행사 로그인
     */
    public void travelAgencLogin() {
        // 여행사 인증 전략으로 변경
        currentStrategy = strategies.get("agency");
        
        System.out.print("agency email? ");
        String email = sc.nextLine();
        System.out.print("password? ");
        String password = sc.nextLine();

        currentStrategy.authenticate(email, password);
    }

    /**
     * 여행사 회원가입
     */
    public void travelAgencSignUp() {
        // 여행사 인증 전략으로 변경
        currentStrategy = strategies.get("agency");
        currentStrategy.register();
    }
    
    /**
     * 사용자 정보 표시
     */
    public void displayUserInfo() {
        currentStrategy.displayUserInfo();
    }
    
    /**
     * 로그아웃
     */
    public void logout() {
        currentStrategy.logout();
    }
    
    /**
     * 로그인 상태 확인
     * @return 현재 로그인 상태 여부
     */
    public boolean isLoggedIn() {
        return currentStrategy != null && currentStrategy.isLoggedIn();
    }
    
    /**
     * 현재 로그인된 일반 사용자 반환
     * @return 로그인된 사용자 (없으면 null)
     */
    public RegisteredPassenger getCurrentUser() {
        if (currentStrategy == strategies.get("user") && currentStrategy.isLoggedIn()) {
            return (RegisteredPassenger) currentStrategy.getCurrentUserObject();
        }
        return null;
    }

    /**
     * 현재 로그인된 여행사 반환
     * @return 로그인된 여행사 (없으면 null)
     */
    public TravelAgency getCurrentAgency() {
        if (currentStrategy == strategies.get("agency") && currentStrategy.isLoggedIn()) {
            return (TravelAgency) currentStrategy.getCurrentUserObject();
        }
        return null;
    }
    
    /**
     * 현재 사용 중인 전략의 이름 반환
     * @return 현재 전략 이름
     */
    public String getCurrentStrategyName() {
        return currentStrategy.getStrategyName();
    }
}
