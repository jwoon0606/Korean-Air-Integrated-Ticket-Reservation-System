package command;

import controller.LoginController;

/**
 * 일반 사용자 회원가입을 위한 명령 구현체
 */
public class SignUpCommand implements Command {
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
            System.out.println("이미 로그인되어 있습니다.");
            return false;
        }
        return true;
    }
    
    @Override
    public String getMenuText() {
        return "2. Sign Up";
    }
}
