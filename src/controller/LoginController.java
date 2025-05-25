package controller;
import strategy.AgencyAuthenticationStrategy;
import strategy.AuthenticationStrategy;
import strategy.UserAuthenticationStrategy;
import user.RegisteredPassenger;
import user.TravelAgency;
import java.util.Scanner;

/** 
* 전략 패턴을 적용한 로그인 컨트롤러
* Context 역할을 수행하여 다양한 인증 전략을 관리합니다.
*/
public class LoginController {
    private final Scanner sc = new Scanner(System.in);
    
    // 전략 패턴: 개별 인증 전략 인스턴스
    private AuthenticationStrategy userStrategy = new UserAuthenticationStrategy();
    private AuthenticationStrategy agencyStrategy = new AgencyAuthenticationStrategy();
    
    // 현재 선택된 인증 전략
    private AuthenticationStrategy currentStrategy;
    private RegisteredPassenger lastSignedUpUser; // 마지막으로 가입한 일반 사용자 정보 임시 저장
    private TravelAgency lastSignedUpAgency; // 마지막으로 가입한 여행사 정보 임시 저장

    /**
     * 기본 생성자: 내부적으로 인증 전략들을 초기화합니다.
     */
    public LoginController() {
        // 기본값으로 일반 사용자 인증 전략 설정
        currentStrategy = this.userStrategy;
    }
    
    /**
     * 의존성 주입을 위한 생성자: 외부에서 개별 전략 객체를 주입받습니다.
     * 테스트 용이성과 확장성을 위해 유지합니다.
     * 
     * @param userStrategy 일반 사용자 인증 전략
     * @param agencyStrategy 여행사 인증 전략
     */
    public LoginController(AuthenticationStrategy userStrategy, AuthenticationStrategy agencyStrategy) {
        this.userStrategy = userStrategy;
        this.agencyStrategy = agencyStrategy;
        
        // 기본값으로 주입된 일반 사용자 인증 전략 설정 
        this.currentStrategy = this.userStrategy;
    }
    
    /**
     * 일반 사용자 로그인
     */
    public void login() {
        // 일반 사용자 인증 전략으로 변경
        currentStrategy = this.userStrategy;
        
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
        currentStrategy = this.userStrategy;
        boolean success = currentStrategy.register(); // register의 반환값(boolean)을 받음

        // 인터페이스를 통해 마지막 가입자 정보를 가져옴
        if (success) {
            Object registeredUser = currentStrategy.getLastRegisteredUser();
            if (registeredUser instanceof RegisteredPassenger) {
                this.lastSignedUpUser = (RegisteredPassenger) registeredUser;
            } else {
                this.lastSignedUpUser = null;
            }
        } else {
            this.lastSignedUpUser = null; // 가입 실패 시 null 처리
        }
    }

    /**
     * 마지막으로 가입한 사용자 삭제 (Undo용)
     */
    public boolean deleteLastSignedUpUser() {
        if (this.lastSignedUpUser != null) {
            // 인터페이스를 통해 사용자 삭제
            boolean deleted = currentStrategy.deleteUser(this.lastSignedUpUser.getEmail());
            if (deleted) {
                System.out.println("Account for " + this.lastSignedUpUser.getEmail() + " has been deleted.");
                this.lastSignedUpUser = null; // 삭제 후 초기화
                return true;
            } else {
                System.out.println("Failed to delete account for " + this.lastSignedUpUser.getEmail() + ". It might have been already deleted or an error occurred during deletion.");
                return false;
            }
        }
        System.out.println("No recently signed up user to delete.");
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
        currentStrategy = this.agencyStrategy;
        
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
        currentStrategy = this.agencyStrategy;
        boolean success = currentStrategy.register();

        // 인터페이스를 통해 마지막 가입 여행사 정보를 가져옴
        if (success) {
            Object registeredUser = currentStrategy.getLastRegisteredUser();
            if (registeredUser instanceof TravelAgency) {
                this.lastSignedUpAgency = (TravelAgency) registeredUser;
            } else {
                this.lastSignedUpAgency = null;
            }
        } else {
            this.lastSignedUpAgency = null;
        }
    }

    /**
     * 마지막으로 가입한 여행사 사용자 삭제 (Undo용)
     */
    public boolean deleteLastSignedUpAgency() {
        if (this.lastSignedUpAgency != null) {
            // 인터페이스를 통해 여행사 삭제
            boolean deleted = currentStrategy.deleteUser(this.lastSignedUpAgency.getEmail());
            if (deleted) {
                System.out.println("Agency account for " + this.lastSignedUpAgency.getEmail() + " has been deleted.");
                this.lastSignedUpAgency = null; // 삭제 후 초기화
                return true;
            } else {
                System.out.println("Failed to delete agency account for " + this.lastSignedUpAgency.getEmail() + ". It might have been already deleted or an error occurred during deletion.");
                return false;
            }
        }
        System.out.println("No recently signed up agency to delete.");
        return false;
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
        if (currentStrategy == this.userStrategy && currentStrategy.isLoggedIn()) {
            return (RegisteredPassenger) currentStrategy.getCurrentUserObject();
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
