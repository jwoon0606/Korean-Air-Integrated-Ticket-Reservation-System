package command.concrete_command;
import command.undo_command.UndoableCommand;
import controller.LoginController;

/**
 * 일반 사용자 로그인을 위한 명령 구현체
 */
public class LoginCommand implements UndoableCommand {
    private LoginController loginController;
    
    public LoginCommand(LoginController loginController) {
        this.loginController = loginController;
    }
    
    @Override
    public void execute() {
        System.out.println();
        loginController.login();
    }
    
    @Override
    public boolean canExecute(boolean isLoggedIn) {
        if (isLoggedIn) {
            System.out.println("already logged in");
            return false;
        }
        return true;
    }
    
    @Override
    public String getMenuText() {
        return "1. Login";
    }

     @Override
    public void undo() {
        // 로그인 명령을 취소하는 것은 로그아웃하는 것과 같습니다.
        // 단, 현재 로그인된 사용자가 이 명령으로 로그인한 사용자인지 확인하는 로직이 필요할 수 있으나,
        // 여기서는 LoginController의 logout 기능을 그대로 사용합니다.
        if (loginController.isLoggedIn()) {
            loginController.logout();
            System.out.println("\nlogged out");
        } else {
            System.out.println("Undo for login is not applicable or already logged out.");
        }
    }
}