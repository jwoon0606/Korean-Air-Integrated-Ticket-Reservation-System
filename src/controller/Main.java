package controller;

import java.util.Scanner;
import user.GuestPassenger;
import user.User;

import command.*;

/**
 * 명령 패턴을 적용한 메인 클래스
 * CommandRegistry를 통해 구체적인 Command 클래스와의 결합도 감소
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
        boolean[] programRunningState = {true}; // 프로그램 실행 상태를 저장하는 참조 변수
        
        // 컨트롤러 생성
        LoginController loginController = new LoginController();
        ReservationController reservationController = new ReservationController();

        // CommandRegistry 초기화
        CommandRegistry.initialize(loginController, reservationController, programRunningState);

        System.out.println("Korean Air Integrated Ticket Reservation System(KTR)\n");
        
        // 프로그램 메인 루프
        while(programRunningState[0]) {
            displayMenu(loginController.isLoggedIn());
            
            System.out.print("=> ");
            int menuChoice = sc.nextInt();
            sc.nextLine(); // 버퍼 비우기
            
            if (CommandRegistry.hasCommand(menuChoice)) {
                Command selectedCommand = CommandRegistry.getCommand(menuChoice);
                if (selectedCommand.canExecute(loginController.isLoggedIn())) {
                    selectedCommand.execute();
                }
            } else {
                System.out.println("Invalid menu selection. Please try again.");
            }
        }

        System.out.println("Program terminated.");
    }
    
    /**
     * 현재 로그인 상태에 따라 적절한 메뉴를 표시
     * 
     * @param isLoggedIn 로그인 상태
     */
    private void displayMenu(boolean isLoggedIn) {
        System.out.println("\nmenu");
        
        // 로그인 상태에 따라 다른 메뉴 표시
        Integer[] validMenuIds = CommandRegistry.getValidMenuIds(isLoggedIn);
        
        for (Integer menuId : validMenuIds) {
            if (menuId != 0) { // 종료 메뉴는 마지막에 표시
                System.out.println(CommandRegistry.getCommand(menuId).getMenuText());
            }
        }
        
        // 항상 표시되는 종료 메뉴
        System.out.println(CommandRegistry.getCommand(0).getMenuText());
    }
}
