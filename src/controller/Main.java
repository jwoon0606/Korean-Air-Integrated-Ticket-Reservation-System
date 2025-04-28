package controller;

import javax.swing.*;
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

        System.out.println("Korean Air Integrated Ticket Reservation System(KTR)\n");
        while(running) {
            System.out.println("menu");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Travel Agency Login");
            System.out.println("4. Travel Agency Sign Up");
            if (loginController.isLoggedIn()) {  // 25.04.28 :: PSY - Only show logout if logged in
                System.out.println("5. Logout");
            }
            System.out.println("0. End Program");
            System.out.print("=> ");

            int menu = sc.nextInt();
            switch (menu) {
                case 1:
                    System.out.println();
                    loginController.login();
                    break;
                case 2:
                    System.out.println();
                    loginController.signUp();
                    break;

                case 3:
                    System.out.println();
                    loginController.travelAgencLogin();
                    break;
                case 4:
                    System.out.println();
                    loginController.travelAgencSignUp();
                    break;
                case 5: // 25.04.28 :: PSY - login Check out
                    if (loginController.isLoggedIn()) {
                        loginController.logout();
                    } else {
                        System.out.println("No user is logged in.\n");
                    }
                    break;
                case 0:
                    running = false;
                    break;
            }
        }

        System.out.println("Program terminated.");
    }
    
    
    
}
