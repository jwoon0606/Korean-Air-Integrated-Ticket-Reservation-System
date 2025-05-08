package command;

/**
 * 로그아웃을 위한 명령 구현체
 */
public class LogoutCommand implements Command {
    private CommandContext context;
    
    public LogoutCommand(CommandContext context) {
        this.context = context;
    }
    
    @Override
    public void execute() {
        System.out.println();
        context.logout();
    }
    
    @Override
    public boolean canExecute(boolean isLoggedIn) {
        if (!isLoggedIn) {
            System.out.println("로그인이 되어있지 않습니다.");
            return false;
        }
        return true;
    }
    
    @Override
    public String getMenuText() {
        return "5. Logout";
    }
}