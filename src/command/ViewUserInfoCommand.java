package command;

/**
 * 사용자 정보 조회를 위한 명령 구현체
 */
public class ViewUserInfoCommand implements Command {
    private CommandContext context;
    
    public ViewUserInfoCommand(CommandContext context) {
        this.context = context;
    }
    
    @Override
    public void execute() {
        System.out.println();
        context.displayUserInfo();
    }
    
    @Override
    public boolean canExecute(boolean isLoggedIn) {
        if (!isLoggedIn) {
            System.out.println("please login first");
            return false;
        }
        return true;
    }
    
    @Override
    public String getMenuText() {
        return "6. View User Info";
    }
}