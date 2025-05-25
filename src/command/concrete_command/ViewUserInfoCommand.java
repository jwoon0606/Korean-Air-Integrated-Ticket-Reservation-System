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

        System.out.println("Returning to the main menu.");
    }
}