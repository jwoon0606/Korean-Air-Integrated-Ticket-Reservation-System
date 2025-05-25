package command.concrete_command;

import command.undo_command.UndoableCommand;
import controller.LoginController;

/**
 * 여행사 회원가입을 위한 명령 구현체
 */
public class AgencySignUpCommand implements UndoableCommand {
    private LoginController loginController;

    public AgencySignUpCommand(LoginController loginController) {
        this.loginController = loginController;
    }

    @Override
    public void execute() { // 반환 타입 void로 변경
        System.out.println(); // UI 간격용 빈 줄
        loginController.travelAgencSignUp(); // 회원가입 시도
    }

    @Override
    public String getMenuText() {
        return "4. Agency Sign Up";
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
    public void undo() {
        // LoginController를 통해 마지막 가입 여행사 삭제
        System.out.println("Attempting to undo agency sign up...");
        boolean undone = loginController.deleteLastSignedUpAgency();
        if (undone) {
            System.out.println("Agency sign up has been successfully undone.");
        } else {
            System.out.println("Failed to undo agency sign up. The agency might not have been created, already deleted, or an error occurred.");
        }
    }
}