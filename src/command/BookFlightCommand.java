package command;

import controller.ReservationController;

/**
 * 항공권 예약을 위한 명령 구현체
 */
public class BookFlightCommand implements Command {
    private ReservationController reservationController;
    
    public BookFlightCommand(ReservationController reservationController) {
        this.reservationController = reservationController;
    }
    
    @Override
    public void execute() {
        reservationController.bookFlight();
    }
    
    @Override
    public boolean canExecute(boolean isLoggedIn) {
        // 로그인 상태와 관계없이 항공권 예약은 가능
        return true;
    }
    
    @Override
    public String getMenuText() {
        return "7. Book Flights";
    }
}