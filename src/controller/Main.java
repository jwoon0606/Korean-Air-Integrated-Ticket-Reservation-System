package controller;

import user.GuestPassenger;
import user.User;

import java.util.Scanner;

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
                case 0:
                    running = false;
                    break;
            }
        }

        System.out.println("Program terminated.");
    }
}
