package command;

import controller.LoginController;

/**
 * 여행사 회원가입을 위한 명령 구현체
 */
public class AgencySignUpCommand implements Command {
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
    public boolean canExecute(boolean isLoggedIn) {
        if (isLoggedIn) {
            System.out.println("이미 로그인되어 있습니다.");
            return false;
        }
        return true;
    }
    
    @Override
    public String getMenuText() {
        return "4. Travel Agency Sign Up";
    }
}