package command;

import java.util.HashMap;
import java.util.Map;
import controller.LoginController;
import controller.ReservationController;

/**
 * Command 객체들을 등록하고 관리하는 레지스트리 클래스
 * Main 클래스와 구체적인 Command 클래스 간의 결합도를 낮추는 역할
 */
public class CommandRegistry {
    private static Map<Integer, Command> commands;
    
    /**
     * 명령 객체들을 초기화하고 등록
     * 
     * @param loginController 로그인 컨트롤러
     * @param reservationController 예약 컨트롤러
     * @param runningRef 프로그램 실행 상태 참조
     */
    public static void initialize(
            LoginController loginController, 
            ReservationController reservationController,
            boolean[] runningRef) {
        
        commands = new HashMap<>();
        
        // 각 메뉴 항목에 대한 명령 객체 생성 및 등록
        registerCommand(1, new LoginCommand(loginController));
        registerCommand(2, new SignUpCommand(loginController));
        registerCommand(3, new AgencyLoginCommand(loginController));
        registerCommand(4, new AgencySignUpCommand(loginController));
        registerCommand(5, new LogoutCommand(loginController));
        registerCommand(6, new ViewUserInfoCommand(loginController));
        registerCommand(7, new BookFlightCommand(reservationController));
        registerCommand(8, new CheckReservationInfoCommand(loginController, reservationController));
        registerCommand(0, new ExitCommand(runningRef));
    }
    
    /**
     * 명령 객체를 레지스트리에 등록
     * 
     * @param menuId 메뉴 ID
     * @param command 등록할 명령 객체
     */
    private static void registerCommand(int menuId, Command command) {
        commands.put(menuId, command);
    }
    
    /**
     * 메뉴 ID에 해당하는 명령 객체 반환
     * 
     * @param menuId 메뉴 ID
     * @return 해당 ID의 명령 객체, 없으면 null
     */
    public static Command getCommand(int menuId) {
        return commands.get(menuId);
    }
    
    /**
     * 명령 객체가 등록되어 있는지 확인
     * 
     * @param menuId 확인할 메뉴 ID
     * @return 등록 여부
     */
    public static boolean hasCommand(int menuId) {
        return commands.containsKey(menuId);
    }
    
    /**
     * 로그인 상태에 따른 유효한 메뉴 ID 목록 반환
     * 
     * @param isLoggedIn 로그인 상태
     * @return 유효한 메뉴 ID 배열
     */
    public static Integer[] getValidMenuIds(boolean isLoggedIn) {
        if (isLoggedIn) {
            return new Integer[]{0, 5, 6, 7, 8}; // 로그인한 상태의 메뉴
        } else {
            return new Integer[]{0, 1, 2, 3, 4, 7, 8}; // 로그인하지 않은 상태의 메뉴
        }
    }
    
    /**
     * 등록된 모든 명령 객체 반환
     * 
     * @return 메뉴 ID와 명령 객체의 맵
     */
    public static Map<Integer, Command> getAllCommands() {
        return new HashMap<>(commands); // 방어적 복사본 반환
    }
} 