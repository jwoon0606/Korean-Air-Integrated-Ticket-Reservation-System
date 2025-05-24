package command.concrete_command;
import command.undo_command.UndoableCommand;
import controller.LoginController;

/**
 * 사용자 정보 조회를 위한 명령 구현체
 */
public class ViewUserInfoCommand implements UndoableCommand {
    private LoginController loginController;
    
    public ViewUserInfoCommand(LoginController loginController) {
        this.loginController = loginController;
    }
    
    @Override
    public void execute() {
        System.out.println();
        loginController.displayUserInfo();
    }
    
    @Override
    public boolean canExecute(boolean isLoggedIn) {
        if (!isLoggedIn) {
            System.out.println("please login first");
            return false;
        }
        return true;
    }
    
    @Override
    public String getMenuText() {
        return "6. View User Info";
    }

    @Override
    public void undo() {
        // 여기에 undo 로직을 구현합니다.
        // 예를 들어, 로그인 상태를 초기화하는 등의 작업을 수행할 수 있습니다.
    }
}