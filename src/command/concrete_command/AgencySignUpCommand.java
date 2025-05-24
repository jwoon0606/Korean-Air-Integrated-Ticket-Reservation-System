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
    public void execute() {
        System.out.println();
        loginController.travelAgencSignUp();
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
        // 여기에 undo 로직을 구현합니다.
        // 예를 들어, 로그인 상태를 초기화하는 등의 작업을 수행할 수 있습니다.
        
    }
}