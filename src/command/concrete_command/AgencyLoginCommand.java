package command.concrete_command;
import command.undo_command.UndoableCommand;
import controller.LoginController;

/**
 * 여행사 로그인을 위한 명령 구현체
 */
public class AgencyLoginCommand implements UndoableCommand {
    private LoginController loginController;
    
    public AgencyLoginCommand(LoginController loginController) {
        this.loginController = loginController;
    }
    
    @Override
    public void execute() {
        System.out.println();
        loginController.travelAgencLogin();
    }
    
    @Override
    public boolean canExecute(boolean isLoggedIn) {
        if (isLoggedIn) {
            System.out.println("You are already logged in.");
            return false;
        }
        return true;
    }
    
    @Override
    public String getMenuText() {
        return "3. Agency Login";
    }

   @Override
    public void undo() {
        if (loginController.isLoggedIn()) {
            loginController.logout();
            System.out.println("Agency login undone (logged out).");
        } else {
            System.out.println("Undo for agency login is not applicable or already logged out.");
        }
    }
}