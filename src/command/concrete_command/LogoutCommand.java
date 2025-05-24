package command.concrete_command;
import command.undo_command.UndoableCommand;
import controller.LoginController;

/**
 * 로그아웃을 위한 명령 구현체
 */
public class LogoutCommand implements UndoableCommand{
    private LoginController loginController;
    
    public LogoutCommand(LoginController loginController) {
        this.loginController = loginController;
    }
    
    @Override
    public void execute() {
        System.out.println();
        loginController.logout();
    }
    
    @Override
    public boolean canExecute(boolean isLoggedIn) {
        if (!isLoggedIn) {
            System.out.println("로그인이 되어있지 않습니다.");
            return false;
        }
        return true;
    }
    
    @Override
    public String getMenuText() {
        return "5. Logout";
    }

    @Override
    public void undo(){
        // 로그아웃 명령의 undo는 지원하지 않음.
        // CommandRegistry의 undoCommand에서 history 스택에서는 pop 됩니다.
        System.out.println("Undo for 'Logout' is not supported and has no effect.");
    }
}