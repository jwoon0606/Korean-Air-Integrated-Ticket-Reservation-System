package controller;

import java.util.Scanner;
import user.GuestPassenger;
import user.User;
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

/**
 * Command Pattern의 Client 역할을 수행하는 메인 클래스
 * CommandRegistry(Invoker)에게 명령 실행을 위임
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
     * Client로서 Invoker에게 명령 실행을 요청
     */
    public void runProgram() {
        boolean[] programRunningState = {true}; // 프로그램 실행 상태를 저장하는 참조 변수
        
        // 컨트롤러 생성 (Receiver들)
        LoginController loginController = new LoginController();
        ReservationController reservationController = new ReservationController();

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
            
            System.out.print("=> ");
            int menuChoice = sc.nextInt();
            sc.nextLine(); // 버퍼 비우기
            
            // Invoker에게 명령 실행 위임 (로그인 상태 전달)
            invoker.executeCommand(menuChoice, loginController.isLoggedIn());
        }

        System.out.println("Program terminated.");
    }
}
