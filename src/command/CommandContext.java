package command;

import controller.LoginController;
import controller.ReservationController;
import dto.ReservationForm;
import java.util.List;
import java.util.Scanner;

/**
 * Command 실행에 필요한 컨텍스트를 제공하는 클래스
 * Command와 Controller 간의 직접적인 결합을 방지
 */
public class CommandContext {
    private LoginController loginController;
    private ReservationController reservationController;
    private boolean[] runningRef;
    private Scanner scanner;
    
    /**
     * 생성자
     * 
     * @param loginController 로그인 컨트롤러
     * @param reservationController 예약 컨트롤러
     * @param runningRef 프로그램 실행 상태 참조
     */
    public CommandContext(
            LoginController loginController,
            ReservationController reservationController,
            boolean[] runningRef) {
        this.loginController = loginController;
        this.reservationController = reservationController;
        this.runningRef = runningRef;
        this.scanner = new Scanner(System.in);
    }
    
    // 로그인 관련 기능 위임 메서드
    
    /**
     * 일반 사용자 로그인 수행
     */
    public void login() {
        loginController.login();
    }
    
    /**
     * 일반 사용자 회원가입 수행
     */
    public void signUp() {
        loginController.signUp();
    }
    
    /**
     * 여행사 로그인 수행
     */
    public void agencyLogin() {
        loginController.travelAgencLogin();
    }
    
    /**
     * 여행사 회원가입 수행
     */
    public void agencySignUp() {
        loginController.travelAgencSignUp();
    }
    
    /**
     * 로그아웃 수행
     */
    public void logout() {
        loginController.logout();
    }
    
    /**
     * 사용자 정보 표시
     */
    public void displayUserInfo() {
        loginController.displayUserInfo();
    }
    
    /**
     * 로그인 상태 확인
     * 
     * @return 로그인 상태
     */
    public boolean isLoggedIn() {
        return loginController.isLoggedIn();
    }
    
    /**
     * 현재 로그인된 일반 사용자 객체 반환
     * 
     * @return 로그인된 일반 사용자 객체
     */
    public Object getCurrentUser() {
        return loginController.getCurrentUser();
    }
    
    /**
     * 현재 로그인된 여행사 객체 반환
     * 
     * @return 로그인된 여행사 객체
     */
    public Object getCurrentAgency() {
        return loginController.getCurrentAgency();
    }
    
    // 예약 관련 기능 위임 메서드
    
    /**
     * 항공편 예약 수행
     */
    public void bookFlight() {
        reservationController.bookFlight();
    }
    
    /**
     * 예약 정보 확인
     */
    public void checkReservationInfo() {
        if (!loginController.isLoggedIn()) {
            System.out.print("예약 ID를 입력하세요: ");
            String id = scanner.nextLine();
            System.out.print("비밀번호를 입력하세요: ");
            String pw = scanner.nextLine();

            ReservationForm form = reservationController.findByReservationIdAndPassword(id, pw);
            if (form != null) {
                System.out.println("예약 상세 정보:");
                System.out.println(form);
            } else {
                System.out.println("예약 정보를 찾을 수 없습니다.");
            }
        } else {
            // 로그인된 사용자의 이메일로 예약 조회
            String email = loginController.getCurrentUser().getEmail();
            List<ReservationForm> reservations = reservationController.findReservationsByEmail(email);

            if (reservations.isEmpty()) {
                System.out.println("등록된 예약이 없습니다.");
                return;
            }

            System.out.println("예약 목록:");
            for (int i = 0; i < reservations.size(); i++) {
                System.out.printf("%d. %s -> %s\n", i + 1,
                        reservations.get(i).getReservedFlights().get(0).getFlight().getDeparture(),
                        reservations.get(i).getReservedFlights().get(0).getFlight().getDestination());
            }

            System.out.print("상세 정보를 볼 예약 번호를 선택하세요: ");
            int idx = scanner.nextInt() - 1;
            scanner.nextLine(); // consume newline
            if (idx >= 0 && idx < reservations.size()) {
                System.out.println(reservations.get(idx));
            } else {
                System.out.println("잘못된 선택입니다.");
            }
        }
    }
    
    // 프로그램 제어 메서드
    
    /**
     * 프로그램 종료
     */
    public void exitProgram() {
        runningRef[0] = false;
    }
} 