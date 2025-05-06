package command;

import controller.LoginController;

/**
 * 로그아웃을 위한 명령 구현체
 */
public class LogoutCommand implements Command {
    private LoginController loginController;
    
    public LogoutCommand(LoginController loginController) {
        this.loginController = loginController;
    }
    
    @Override
    public void execute() {
        loginController.logout();
        System.out.println("로그아웃 되었습니다.");
    }
    
    @Override
    public boolean canExecute(boolean isLoggedIn) {
        if (!isLoggedIn) {
            System.out.println("로그인 상태가 아닙니다.");
            return false;
        }
        return true;
    }
    
    @Override
    public String getMenuText() {
        return "5. Logout";
    }
}