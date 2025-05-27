package controller;

import java.util.Scanner;
import command.concrete_command.AgencyLoginCommand;
import command.concrete_command.AgencySignUpCommand;
import command.concrete_command.BookFlightCommand;
import command.concrete_command.CheckReservationInfoCommand;
import command.concrete_command.ExitCommand;
import command.concrete_command.LoginCommand;
import command.concrete_command.LogoutCommand;
import command.concrete_command.SignUpCommand;
import command.concrete_command.ViewUserInfoCommand;
import command.invoker.CommandRegistry;
import strategy.AgencyAuthenticationStrategy;
import strategy.UserAuthenticationStrategy;

/**
 * Command Pattern의 Client 역할을 수행하는 메인 클래스
 * CommandRegistry(Invoker)에게 명령 실행을 위임
 */
public class Main {
    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Main main = new Main();
        main.runProgram();
    }

    /**
     * 프로그램 실행 메소드
     * Client로서 Invoker에게 명령 실행을 요청
     */
    public void runProgram() {
        boolean[] programRunningState = {true}; // 프로그램 실행 상태를 저장하는 참조 변수

        UserAuthenticationStrategy userStrategy = new UserAuthenticationStrategy();
        AgencyAuthenticationStrategy agencyStrategy = new AgencyAuthenticationStrategy();
        
        // 컨트롤러 생성 (Receiver들)
        LoginController loginController = new LoginController(userStrategy, agencyStrategy);
        ReservationController reservationController = ReservationController.getReservationController();

        // Invoker 생성
        CommandRegistry invoker = new CommandRegistry();
        
        // Command 객체들 생성 및 등록
        invoker.setCommand(0, new ExitCommand(programRunningState));
        invoker.setCommand(1, new LoginCommand(loginController));
        invoker.setCommand(2, new SignUpCommand(loginController));
        invoker.setCommand(3, new AgencyLoginCommand(loginController));
        invoker.setCommand(4, new AgencySignUpCommand(loginController));
        invoker.setCommand(5, new LogoutCommand(loginController));
        invoker.setCommand(6, new ViewUserInfoCommand(loginController));
        invoker.setCommand(7, new BookFlightCommand(reservationController));
        invoker.setCommand(8, new CheckReservationInfoCommand(loginController, reservationController));

        System.out.println("Korean Air Integrated Ticket Reservation System(KTR)\n");
        
        // 프로그램 메인 루프
        while(programRunningState[0]) {
            invoker.displayMenu(loginController.isLoggedIn());
            System.out.println("-1. Undo last operation"); // Option to undo the previous action
            
            System.out.print("=> ");
            int menuChoice = -1; // 기본값을 -1과 같이 유효하지 않은 메뉴 번호로 설정
            if (sc.hasNextInt()) {
                menuChoice = sc.nextInt();
            }
            sc.nextLine(); // 버퍼 비우기 (정수 입력 후 남은 줄바꿈 문자 제거)
            
            if (menuChoice == -1) { // 사용자가 99를 입력하면 실행 취소
                invoker.undoCommand();
            } else if (menuChoice != -1) { // 유효한 정수 입력인 경우
                // Invoker에게 명령 실행 위임 (로그인 상태 전달)
                invoker.executeCommand(menuChoice, loginController.isLoggedIn());
            } else {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        System.out.println("Program terminated.");
        sc.close(); // Scanner 리소스 정리
    }
}
