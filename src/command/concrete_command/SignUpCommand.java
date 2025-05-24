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
    public void execute() {
        System.out.println();
        loginController.signUp();
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
        // 여기에 undo 로직을 구현합니다.
        // 예를 들어, 로그인 상태를 초기화하는 등의 작업을 수행할 수 있습니다.
    }
}
