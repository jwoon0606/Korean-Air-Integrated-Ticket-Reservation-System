package command;

/**
 * 여행사 로그인을 위한 명령 구현체
 */
public class AgencyLoginCommand implements Command {
    private CommandContext context;
    
    public AgencyLoginCommand(CommandContext context) {
        this.context = context;
    }
    
    @Override
    public void execute() {
        System.out.println();
        context.agencyLogin();
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
        return "3. Agency Login";
    }
}