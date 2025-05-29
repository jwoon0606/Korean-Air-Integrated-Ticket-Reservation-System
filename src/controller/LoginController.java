package controller;
import strategy.AuthenticationStrategy;
import user.*;
import user.RegisteredPassenger;
import user.TravelAgency;
import java.util.Scanner;

public class LoginController {
    private final Scanner sc = new Scanner(System.in);
    private final AuthenticationStrategy userStrategy;
    private final AuthenticationStrategy agencyStrategy;
    private AuthenticationStrategy currentStrategy;
    private RegisteredPassenger lastSignedUpUser;
    private TravelAgency lastSignedUpAgency;

    public LoginController(AuthenticationStrategy userStrategy, AuthenticationStrategy agencyStrategy) {
        this.userStrategy = userStrategy;
        this.agencyStrategy = agencyStrategy;
        this.currentStrategy = this.userStrategy;
    }

    public void login() {
        currentStrategy = this.userStrategy;
        System.out.print("email? ");
        String email = sc.nextLine();
        System.out.print("password? ");
        String password = sc.nextLine();
        currentStrategy.authenticate(email, password);
    }

    public void signUp() {
        currentStrategy = this.userStrategy;
        boolean success = currentStrategy.register();
        if (success) {
            Object registeredUser = currentStrategy.getLastRegisteredUser();
            if (registeredUser instanceof RegisteredPassenger) {
                this.lastSignedUpUser = (RegisteredPassenger) registeredUser;
            } else {
                this.lastSignedUpUser = null;
            }
        } else {
            this.lastSignedUpUser = null;
        }
    }

    public boolean deleteLastSignedUpUser() {
        if (this.lastSignedUpUser != null) {
            boolean deleted = currentStrategy.deleteUser(this.lastSignedUpUser.getEmail());
            if (deleted) {
                System.out.println("Account for " + this.lastSignedUpUser.getEmail() + " has been deleted.");
                this.lastSignedUpUser = null;
                return true;
            } else {
                System.out.println("Failed to delete account for " + this.lastSignedUpUser.getEmail() + ". It might have been already deleted or an error occurred during deletion.");
                return false;
            }
        }
        System.out.println("No recently signed up user to delete.");
        return false;
    }

    public RegisteredPassenger getLastSignedUpUser() {
        return this.lastSignedUpUser;
    }

    public void travelAgencLogin() {
        currentStrategy = this.agencyStrategy;
        System.out.print("agency email? ");
        String email = sc.nextLine();
        System.out.print("password? ");
        String password = sc.nextLine();
        currentStrategy.authenticate(email, password);
    }

    public void travelAgencSignUp() {
        currentStrategy = this.agencyStrategy;
        boolean success = currentStrategy.register();
        if (success) {
            Object registeredUser = currentStrategy.getLastRegisteredUser();
            if (registeredUser instanceof TravelAgency) {
                this.lastSignedUpAgency = (TravelAgency) registeredUser;
            } else {
                this.lastSignedUpAgency = null;
            }
        } else {
            this.lastSignedUpAgency = null;
        }
    }

    public boolean deleteLastSignedUpAgency() {
        if (this.lastSignedUpAgency != null) {
            boolean deleted = currentStrategy.deleteUser(this.lastSignedUpAgency.getEmail());
            if (deleted) {
                System.out.println("Agency account for " + this.lastSignedUpAgency.getEmail() + " has been deleted.");
                this.lastSignedUpAgency = null;
                return true;
            } else {
                System.out.println("Failed to delete agency account for " + this.lastSignedUpAgency.getEmail() + ". It might have been already deleted or an error occurred during deletion.");
                return false;
            }
        }
        System.out.println("No recently signed up agency to delete.");
        return false;
    }

    public void displayUserInfo() {
        currentStrategy.displayUserInfo();
    }

    public void logout() {
        currentStrategy.logout();
    }

    public boolean isLoggedIn() {
        return currentStrategy != null && currentStrategy.isLoggedIn();
    }

    public User getCurrentUser() {
        if (currentStrategy != null && currentStrategy.isLoggedIn()) {
            Object user = currentStrategy.getCurrentUserObject();
            if (user instanceof User) {
                return (User) user;
            }
        }      
        return new GuestPassenger(); // 로그인 안 되어 있으면 Guest로 간주
    }

    public String getCurrentStrategyName() {
        return currentStrategy.getStrategyName();
    }
}