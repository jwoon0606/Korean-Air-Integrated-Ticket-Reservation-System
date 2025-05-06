package command;

import controller.LoginController;

/**
 * 사용자 정보 보기를 위한 명령 구현체
 */
public class ViewUserInfoCommand implements Command {
    private LoginController loginController;
    
    public ViewUserInfoCommand(LoginController loginController) {
        this.loginController = loginController;
    }
    
    @Override
    public void execute() {
        loginController.displayUserInfo();
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
        return "6. View User Info";
    }
}