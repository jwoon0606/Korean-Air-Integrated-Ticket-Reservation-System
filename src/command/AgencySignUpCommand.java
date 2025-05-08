package command;

/**
 * 여행사 회원가입을 위한 명령 구현체
 */
public class AgencySignUpCommand implements Command {
    private CommandContext context;
    
    public AgencySignUpCommand(CommandContext context) {
        this.context = context;
    }
    
    @Override
    public void execute() {
        System.out.println();
        context.agencySignUp();
    }
    
    @Override
    public boolean canExecute(boolean isLoggedIn) {
        if (isLoggedIn) {
            System.out.println("먼저 로그아웃해주세요.");
            return false;
        }
        return true;
    }
    
    @Override
    public String getMenuText() {
        return "4. Agency Sign Up";
    }
}