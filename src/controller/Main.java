package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import user.GuestPassenger;
import user.User;

import command.*;

/**
 * 명령 패턴을 적용한 메인 클래스
 */
public class Main {
    Scanner sc = new Scanner(System.in);
    User user = new GuestPassenger();

    public static void main(String[] args) {
        Main main = new Main();
        main.runProgram();
    }

    /**
     * 프로그램 실행 메소드
     * 명령 패턴을 사용하여 메뉴 처리
     */
    public void runProgram() {
        boolean[] runningRef = {true}; // 참조를 통해 ExitCommand에서 변경할 수 있도록 배열로 래핑
        
        LoginController loginController = new LoginController();
        ReservationController reservationController = new ReservationController();

        // 명령 패턴: 각 메뉴 항목을 명령 객체로 캡슐화
        Map<Integer, Command> commands = initializeCommands(loginController, reservationController, runningRef);

        System.out.println("Korean Air Integrated Ticket Reservation System(KTR)\n");
        
        // 프로그램 메인 루프
        while(runningRef[0]) {
            displayMenu(commands, loginController.isLoggedIn());
            
            System.out.print("=> ");
            int menuChoice = sc.nextInt();
            sc.nextLine(); // 버퍼 비우기
            
            if (commands.containsKey(menuChoice)) {
                Command selectedCommand = commands.get(menuChoice);
                if (selectedCommand.canExecute(loginController.isLoggedIn())) {
                    selectedCommand.execute();
                }
            } else {
                System.out.println("잘못된 메뉴 선택입니다. 다시 선택해주세요.");
            }
        }

        System.out.println("프로그램이 종료되었습니다.");
    }
    
    /**
     * 명령 객체들을 초기화하고 메뉴 번호와 매핑
     * 
     * @param loginController 로그인 컨트롤러
     * @param reservationController 예약 컨트롤러
     * @param runningRef 프로그램 실행 상태 참조
     * @return 메뉴 번호별 명령 객체 맵
     */
    private Map<Integer, Command> initializeCommands(
            LoginController loginController, 
            ReservationController reservationController,
            boolean[] runningRef) {
        
        Map<Integer, Command> commands = new HashMap<>();
        
        // 각 메뉴 항목에 대한 명령 객체 생성 및 등록
        commands.put(1, new LoginCommand(loginController));
        commands.put(2, new SignUpCommand(loginController));
        commands.put(3, new AgencyLoginCommand(loginController));
        commands.put(4, new AgencySignUpCommand(loginController));
        commands.put(5, new LogoutCommand(loginController));
        commands.put(6, new ViewUserInfoCommand(loginController));
        commands.put(7, new BookFlightCommand(reservationController));
        commands.put(0, new ExitCommand(runningRef));
        
        return commands;
    }
    
    /**
     * 현재 로그인 상태에 따라 적절한 메뉴를 표시
     * 
     * @param commands 명령 객체 맵
     * @param isLoggedIn 로그인 상태
     */
    private void displayMenu(Map<Integer, Command> commands, boolean isLoggedIn) {
        System.out.println("\nmenu");
        
        // 로그인 상태에 따라 다른 메뉴 표시
        if (!isLoggedIn) {
            // 로그인하지 않은 상태의 메뉴
            System.out.println(commands.get(1).getMenuText());
            System.out.println(commands.get(2).getMenuText());
            System.out.println(commands.get(3).getMenuText());
            System.out.println(commands.get(4).getMenuText());
            System.out.println(commands.get(7).getMenuText());
        } else {
            // 로그인한 상태의 메뉴
            System.out.println(commands.get(5).getMenuText());
            System.out.println(commands.get(6).getMenuText());
            System.out.println(commands.get(7).getMenuText());
        }
        
        // 항상 표시되는 종료 메뉴
        System.out.println(commands.get(0).getMenuText());
    }
}
