package controller;
import strategy.AgencyAuthenticationStrategy;
import strategy.AuthenticationStrategy;
import strategy.UserAuthenticationStrategy;
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
    private RegisteredPassenger lastSignedUpUser; // 마지막으로 가입한 일반 사용자 정보 임시 저장
    private TravelAgency lastSignedUpAgency; // 마지막으로 가입한 여행사 정보 임시 저장

    /**
     * 기본 생성자: 내부적으로 인증 전략들을 초기화합니다.
     * Main 클래스가 구체적인 전략 클래스에 의존하지 않도록 합니다.
     */
    public LoginController() {
        strategies = new HashMap<>();
        
        // 인증 전략 등록 - 이 부분은 LoginController 내부에서만 알고 있음
        strategies.put("user", new UserAuthenticationStrategy());
        strategies.put("agency", new AgencyAuthenticationStrategy());
        
        // 기본값으로 일반 사용자 인증 전략 설정
        currentStrategy = strategies.get("user");
    }
    
    /**
     * 의존성 주입을 위한 생성자: 외부에서 전략 맵을 주입받습니다.
     * 테스트 용이성과 확장성을 위해 유지합니다.
     * 
     * @param strategies 인증 전략 맵
     */
    public LoginController(Map<String, AuthenticationStrategy> strategies) {
        this.strategies = strategies;
        
        // 기본값으로 일반 사용자 인증 전략 설정 (맵에 "user" 키가 있다고 가정)
        this.currentStrategy = strategies.get("user");
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
        boolean success = currentStrategy.register(); // register의 반환값(boolean)을 받음

        // UserAuthenticationStrategy 내부에서 마지막 가입자 정보를 관리하도록 변경
        // LoginController는 UserAuthenticationStrategy의 getter를 통해 마지막 가입자 정보를 가져옴
        if (success && currentStrategy instanceof UserAuthenticationStrategy) {
            this.lastSignedUpUser = ((UserAuthenticationStrategy) currentStrategy).getLastRegisteredUser();
        } else {
            this.lastSignedUpUser = null; // 가입 실패 또는 다른 타입 반환 시 null 처리
        }
    }

    /**
     * 마지막으로 가입한 사용자 삭제 (Undo용)
     */
    public boolean deleteLastSignedUpUser() {
        if (this.lastSignedUpUser != null && currentStrategy instanceof UserAuthenticationStrategy) {
            // UserAuthenticationStrategy에 실제 사용자 삭제 로직이 구현되어 있어야 함
            // UserAuthenticationStrategy.deleteUser(String email)을 호출한다고 가정합니다.
            boolean deleted = ((UserAuthenticationStrategy) currentStrategy).deleteUser(this.lastSignedUpUser.getEmail());
            if (deleted) {
                System.out.println("Account for " + this.lastSignedUpUser.getEmail() + " has been deleted.");
                this.lastSignedUpUser = null; // 삭제 후 초기화
                return true;
            } else {
                System.out.println("Failed to delete account for " + this.lastSignedUpUser.getEmail() + ". It might have been already deleted or an error occurred during deletion.");
                return false;
            }
        }
        System.out.println("No recently signed up user to delete, user was not a regular user, or an issue occurred.");
        return false;
    }

    /**
     * 마지막으로 가입한 사용자 정보를 반환합니다.
     * @return 마지막으로 가입한 RegisteredPassenger 객체, 없으면 null
     */
    public RegisteredPassenger getLastSignedUpUser() {
        return this.lastSignedUpUser;
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
        boolean success = currentStrategy.register();

        if (success && currentStrategy instanceof AgencyAuthenticationStrategy) {
            this.lastSignedUpAgency = ((AgencyAuthenticationStrategy) currentStrategy).getLastRegisteredAgency();
        } else {
            this.lastSignedUpAgency = null;
        }
    }

    /**
     * 마지막으로 가입한 여행사 사용자 삭제 (Undo용)
     */
    public boolean deleteLastSignedUpAgency() {
        if (this.lastSignedUpAgency != null && currentStrategy instanceof AgencyAuthenticationStrategy) {
            boolean deleted = ((AgencyAuthenticationStrategy) currentStrategy).deleteAgency(this.lastSignedUpAgency.getEmail());
            if (deleted) {
                System.out.println("Agency account for " + this.lastSignedUpAgency.getEmail() + " has been deleted.");
                this.lastSignedUpAgency = null; // 삭제 후 초기화
                return true;
            } else {
                System.out.println("Failed to delete agency account for " + this.lastSignedUpAgency.getEmail() + ". It might have been already deleted or an error occurred during deletion.");
                return false;
            }
        }
        System.out.println("No recently signed up agency to delete, or an issue occurred.");
        return false;
    }

    /**
     * 마지막으로 가입한 여행사 정보를 반환합니다.
     * @return 마지막으로 가입한 TravelAgency 객체, 없으면 null
     */
    public TravelAgency getLastSignedUpAgency() {
        return this.lastSignedUpAgency;
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
