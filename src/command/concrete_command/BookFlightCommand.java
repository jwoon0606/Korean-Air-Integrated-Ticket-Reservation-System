package command.concrete_command;
import command.undo_command.UndoableCommand;
import controller.ReservationController;

/**
 * 항공편 예약을 위한 명령 구현체
 */
public class BookFlightCommand implements UndoableCommand {
    private ReservationController reservationController;
    
    public BookFlightCommand(ReservationController reservationController) {
        this.reservationController = reservationController;
    }
    
    @Override
    public void execute() {
        System.out.println();
        reservationController.bookFlight();
    }
    
    @Override
    public boolean canExecute(boolean isLoggedIn) {
        return true; // 로그인 여부와 관계없이 실행 가능
    }
    
    @Override
    public String getMenuText() {
        return "7. Book Flight";
    }

    @Override
    public void undo() {
        // 여기에 undo 로직을 구현합니다.
        // 예를 들어, 예약을 취소하는 등의 작업을 수행할 수 있습니다.

    }
}