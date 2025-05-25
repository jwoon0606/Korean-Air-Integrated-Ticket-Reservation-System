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
        // 항공편 예약 취소 로직을 ReservationController에 위임합니다.
        // ReservationController에 cancelMostRecentBooking()과 같은 메소드가 있다고 가정합니다.
        // 이 메소드는 가장 최근에 생성된 예약을 찾아 삭제하거나 '취소됨' 상태로 변경합니다.
        if (reservationController.cancelMostRecentBooking()) {
            System.out.println("\nreservation has been canceled.");
        } else {
            System.out.println("No flight booking to cancel or cancellation failed.");
        }
    }
}