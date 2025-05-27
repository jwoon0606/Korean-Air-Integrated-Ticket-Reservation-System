package command.concrete_command;
import command.undo_command.UndoableCommand;
import controller.LoginController;

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
        if (loginController.isLoggedIn()) {
            loginController.logout();
            System.out.println("\nlogged out");
        } else {
            System.out.println("Undo for login is not applicable or already logged out.");
        }
    }
}