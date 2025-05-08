package command;

/**
 * 프로그램 종료를 위한 명령 구현체
 */
public class ExitCommand implements Command {
    private CommandContext context;
    
    public ExitCommand(CommandContext context) {
        this.context = context;
    }
    
    @Override
    public void execute() {
        System.out.println();
        context.exitProgram();
    }
    
    @Override
    public boolean canExecute(boolean isLoggedIn) {
        return true; // 종료는 항상 가능
    }
    
    @Override
    public String getMenuText() {
        return "0. Exit";
    }
}