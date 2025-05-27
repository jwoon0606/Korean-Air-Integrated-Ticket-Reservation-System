package command.concrete_command;
import command.undo_command.UndoableCommand;
import controller.LoginController;
import controller.ReservationController;

public class BookFlightCommand implements UndoableCommand {
    private ReservationController reservationController;
    private LoginController loginController; 
    private DeleteReservationCommand deleteReservationCommand; 
    
    public BookFlightCommand(ReservationController reservationController, LoginController loginController) {
        this.reservationController = reservationController;
        this.loginController = loginController;
        this.deleteReservationCommand = new DeleteReservationCommand(this.reservationController, this.loginController);
    }
    
    @Override
    public void execute() {
        System.out.println();
        reservationController.bookFlight();
    }
    
    @Override
    public boolean canExecute(boolean isLoggedIn) {
        return true; 
    }
    
    @Override
    public String getMenuText() {
        return "7. Book Flight";
    }

    @Override
    public void undo() {
        System.out.println("Attempting to undo flight booking by cancelling the most recent reservation...");
        deleteReservationCommand.execute(); 
    }
}