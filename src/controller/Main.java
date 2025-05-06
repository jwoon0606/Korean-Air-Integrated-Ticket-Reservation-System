package controller;

import java.util.Scanner;
import user.GuestPassenger;
import user.User;


public class Main {
    Scanner sc = new Scanner(System.in);
    User user = new GuestPassenger();

    public static void main(String[] args) {
        Main main = new Main();
        main.runProgram();
    }

    public void runProgram() {
        boolean running = true;
        LoginController loginController = new LoginController();
        ReservationController reservationController = new ReservationController();

        System.out.println("Korean Air Integrated Ticket Reservation System(KTR)\n");
        while(running) {
            System.out.println("menu");
            
         // 25.04.28 :: PSY - If not login, menu
            if (!loginController.isLoggedIn()) {
                System.out.println("1. Login");
                System.out.println("2. Sign Up");
                System.out.println("3. Travel Agency Login");
                System.out.println("4. Travel Agency Sign Up");
                System.out.println("7. Book Flights");
            } else {
                // If login, menu
                System.out.println("5. Logout");
                System.out.println("6. View User Info");
            }

            System.out.println("0. End Program");
            System.out.print("=> ");

            int menu = sc.nextInt();
            switch (menu) {
            case 1:
                if (!loginController.isLoggedIn()) {
                    System.out.println();
                    loginController.login();
                } else {
                    System.out.println("You are already logged in.");
                }
                break;
            case 2:
                if (!loginController.isLoggedIn()) {
                    System.out.println();
                    loginController.signUp();
                } else {
                    System.out.println("You are already logged in.");
                }
                break;
            case 3:
                if (!loginController.isLoggedIn()) {
                    System.out.println();
                    loginController.travelAgencLogin();
                } else {
                    System.out.println("You are already logged in.");
                }
                break;
            case 4:
                if (!loginController.isLoggedIn()) {
                    System.out.println();
                    loginController.travelAgencSignUp();
                } else {
                    System.out.println("You are already logged in.");
                }
                break;
            case 5:
                if (loginController.isLoggedIn()) {
                    loginController.logout();
                    System.out.println("Logged out successfully.");
                } else {
                    System.out.println("You are not logged in.");
                }
                break;
            case 6:
                if (loginController.isLoggedIn()) {
                    loginController.displayUserInfo();
                } else {
                    System.out.println("You are not logged in.");
                }
                break;
            case 7:
                reservationController.bookFlight();
                break;
            case 0:
                running = false;
                break;
            }
        }

        System.out.println("Program terminated.");
    }
    
    
    
}
