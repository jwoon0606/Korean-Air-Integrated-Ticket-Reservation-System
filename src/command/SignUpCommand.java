package command;

/**
 * 일반 사용자 회원가입을 위한 명령 구현체
 */
public class SignUpCommand implements Command {
    private CommandContext context;
    
    public SignUpCommand(CommandContext context) {
        this.context = context;
    }
    
    @Override
    public void execute() {
        System.out.println();
        context.signUp();
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
    public String getMenuText() {
        return "2. Sign Up";
    }
}
