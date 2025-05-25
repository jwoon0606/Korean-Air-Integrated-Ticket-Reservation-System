package command.concrete_command;

import command.undo_command.UndoableCommand;
import controller.LoginController;

import java.util.Scanner;

/**
 * 일반 사용자 회원가입을 위한 명령 구현체
 */
public class SignUpCommand implements UndoableCommand {
    private LoginController loginController;
    private Scanner scanner; // 사용자 입력용 Scanner

    public SignUpCommand(LoginController loginController) {
        this.loginController = loginController;
        this.scanner = new Scanner(System.in); // Scanner 초기화
    }

    @Override
    public void execute() { // 반환 타입 void로 변경
        System.out.println(); // UI 간격용 빈 줄
        loginController.signUp(); // 회원가입 시도 (내부적으로 성공/실패 메시지 출력 및 lastSignedUpUser 설정)

        if (loginController.getLastSignedUpUser() != null) {
            System.out.print("Undo sign up? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            if ("yes".equals(response)) {
                undo(); // 사용자가 'yes'를 입력하면 undo 실행
            } else {
                System.out.println("Sign up process completed. Returning to menu.");
            }
        } else {
            System.out.println("Returning to menu.");
        }
    }

    @Override
    public boolean canExecute(boolean isLoggedIn) {
        if (isLoggedIn) {
            System.out.println("please logout first");
            return false;
        }
        return true;
    }

    @Override
    public String getMenuText() {
        return "2. Sign Up";
    }

    @Override
    public void undo() {
        // LoginController를 통해 마지막 가입 사용자 삭제
        System.out.println("Attempting to undo sign up...");
        boolean undone = loginController.deleteLastSignedUpUser(); // 이 메서드는 LoginController에 구현되어 있어야 함
        if (undone) {
            System.out.println("Sign up has been successfully undone.");
        } else {
            System.out.println("Failed to undo sign up. The user might not have been created, already deleted, or an error occurred.");
        }
    }
}
