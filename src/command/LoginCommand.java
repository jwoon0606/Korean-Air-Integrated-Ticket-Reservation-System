package command;

/**
 * 일반 사용자 로그인을 위한 명령 구현체
 */
public class LoginCommand implements Command {
    private CommandContext context;
    
    public LoginCommand(CommandContext context) {
        this.context = context;
    }
    
    @Override
    public void execute() {
        System.out.println();
        context.login();
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
        return "1. Login";
    }
}