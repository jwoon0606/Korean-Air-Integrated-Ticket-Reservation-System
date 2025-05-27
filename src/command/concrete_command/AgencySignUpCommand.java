package command.concrete_command;

import command.undo_command.UndoableCommand;
import controller.LoginController;

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
        System.out.println("Attempting to undo agency sign up...");
        boolean undone = loginController.deleteLastSignedUpAgency();
        if (undone) {
            System.out.println("Agency sign up has been successfully undone.");
        } else {
            System.out.println("Failed to undo agency sign up. The agency might not have been created, already deleted, or an error occurred.");
        }
    }
}