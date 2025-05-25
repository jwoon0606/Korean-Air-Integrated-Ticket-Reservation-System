package command.concrete_command;

import command.undo_command.UndoableCommand;
import controller.LoginController;

/**
 * 일반 사용자 회원가입을 위한 명령 구현체
 */
public class SignUpCommand implements UndoableCommand {
    private LoginController loginController;

    public SignUpCommand(LoginController loginController) {
        this.loginController = loginController;
    }

    @Override
    public void execute() { // 반환 타입 void로 변경
        System.out.println(); // UI 간격용 빈 줄
        loginController.signUp(); // 회원가입 시도 (내부적으로 성공/실패 메시지 출력 및 lastSignedUpUser 설정) 
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
    
        System.out.println("Attempting to undo sign up...");
        boolean undone = loginController.deleteLastSignedUpUser(); // 이 메서드는 LoginController에 구현되어 있어야 함
        if (undone) {
            System.out.println("Sign up has been successfully undone.");
        } else {
            System.out.println("Failed to undo sign up. The user might not have been created, already deleted, or an error occurred.");
        }
    }
}
