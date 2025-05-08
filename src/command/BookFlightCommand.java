package command;

/**
 * 항공편 예약을 위한 명령 구현체
 */
public class BookFlightCommand implements Command {
    private CommandContext context;
    
    public BookFlightCommand(CommandContext context) {
        this.context = context;
    }
    
    @Override
    public void execute() {
        System.out.println();
        context.bookFlight();
    }
    
    @Override
    public boolean canExecute(boolean isLoggedIn) {
        return true; // 로그인 여부와 관계없이 실행 가능
    }
    
    @Override
    public String getMenuText() {
        return "7. Book Flight";
    }
}