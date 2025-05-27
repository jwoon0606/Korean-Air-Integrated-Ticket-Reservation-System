package command.concrete_command;

import command.undo_command.UndoableCommand;
import controller.LoginController; // Added import
import controller.ReservationController;
import dto.ReservationForm;
import user.RegisteredPassenger; // Added import

public class DeleteReservationCommand implements UndoableCommand {
    private ReservationController reservationController;
    private LoginController loginController; // Added
    private ReservationForm lastCancelledReservation;

    // Updated constructor
    public DeleteReservationCommand(ReservationController reservationController, LoginController loginController) {
        this.reservationController = reservationController;
        this.loginController = loginController; // Added
    }

    @Override
    public void execute() {
        if (!loginController.isLoggedIn()) {
            System.out.println("\nLogin is required to cancel a reservation.");
            this.lastCancelledReservation = null;
            return;
        }

        RegisteredPassenger currentUser = loginController.getCurrentUser();
        if (currentUser == null) {
            System.out.println("\nCould not identify the current user.");
            this.lastCancelledReservation = null;
            return;
        }
        String userEmail = currentUser.getEmail();
        
        // Call a new method in ReservationController
        this.lastCancelledReservation = reservationController.deleteReservationForUser(userEmail); 

        if (this.lastCancelledReservation != null) {
            System.out.println("\nYour most recent reservation has been canceled.");
        } else {
            System.out.println("\nNo reservation found for your account to cancel, or cancellation failed.");
        }
    }

    @Override
    public void undo() {
        if (lastCancelledReservation != null) {
            if (reservationController.restoreReservation(lastCancelledReservation)) {
                System.out.println("Cancellation undone. Reservation for " + lastCancelledReservation.getId() + " restored.");
                lastCancelledReservation = null; 
            } else {
                System.out.println("Failed to undo cancellation.");
            }
        } else {
            System.out.println("No reservation cancellation to undo.");
        }
    }

    @Override
    public boolean canExecute(boolean isLoggedIn) {
        // Only allow execution if a user is logged in
        return isLoggedIn; 
    }

    @Override
    public String getMenuText() {
        return "9. Cancel My Most Recent Reservation"; 
    }
}
