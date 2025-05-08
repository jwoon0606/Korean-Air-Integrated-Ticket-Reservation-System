package command;

/**
 * 예약 정보 확인을 위한 명령 구현체
 */
public class CheckReservationInfoCommand implements Command {
    private CommandContext context;

    public CheckReservationInfoCommand(CommandContext context) {
        this.context = context;
    }

    @Override
    public void execute() {
        System.out.println();
        context.checkReservationInfo();
    }

    @Override
    public String getMenuText() {
        return "8. Check Reservation Info";
    }

    @Override
    public boolean canExecute(boolean isLoggedIn) {
        return true;
    }
}
