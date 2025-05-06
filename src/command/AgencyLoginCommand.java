package command;

import controller.LoginController;

/**
 * 여행사 로그인을 위한 명령 구현체
 */
public class AgencyLoginCommand implements Command {
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
            System.out.println("이미 로그인되어 있습니다.");
            return false;
        }
        return true;
    }
    
    @Override
    public String getMenuText() {
        return "3. Travel Agency Login";
    }
}