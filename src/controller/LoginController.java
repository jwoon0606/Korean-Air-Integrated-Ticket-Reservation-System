package controller;
import user.RegisteredPassenger;
import user.TravelAgency;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class LoginController {
    ArrayList<RegisteredPassenger> userList;
    ArrayList<TravelAgency> agencyList;
    private final Scanner sc = new Scanner(System.in);
    final String fileName = "src/file/UserList.txt";
    final String agencyFileName = "src/file/AgencyList.txt";
    private int id;
    private int agencyId;

    public LoginController() {
        userList = new ArrayList<>();
        agencyList = new ArrayList<>();
        loadData();
        loadAgencyData();
    }

    public void signUp() {
        System.out.println("Sign Up Page");
        int id = ++this.id;
        System.out.print("name? "); String name = sc.nextLine();
        System.out.print("email? "); String email = sc.nextLine();
        System.out.print("password? "); String password = sc.nextLine();
        System.out.print("phone number? "); String phoneNumber = sc.nextLine();
        System.out.print("gender? "); String gender = sc.nextLine();
        System.out.print("birth date? "); String birthDate = sc.nextLine();

        RegisteredPassenger newUser = new RegisteredPassenger(id,name,email,password,phoneNumber,gender,birthDate);
        userList.add(newUser);
        System.out.println(newUser.getName()+ " registered");
        saveData();
    }

    public void login() {
        System.out.print("email? ");
        String email = sc.nextLine();
        System.out.print("password? ");
        String password = sc.nextLine();

        for (RegisteredPassenger user : userList) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                System.out.println("Welcome, " + user.getName() + "\n");
                return;
            }
        }
        System.out.println("Login failed.\n");
    }

    public void travelAgencSignUp() {
        System.out.println("Travel Agency Sign Up Page");
        int id = ++this.agencyId;
        System.out.print("Agency name? "); String agencyName = sc.nextLine();
        System.out.print("Email? "); String email = sc.nextLine();
        System.out.print("Password? "); String password = sc.nextLine();
        System.out.print("Phone number? "); String phoneNumber = sc.nextLine();

        TravelAgency newAgency = new TravelAgency(id, email, password, phoneNumber, agencyName);
        agencyList.add(newAgency);
        System.out.println(newAgency.getAgencyName() + " agency registered");
        saveAgencyData();
    }

    public void travelAgencLogin() {
        System.out.print("TravelAgency email? ");
        String email = sc.nextLine();
        System.out.print("password? ");
        String password = sc.nextLine();

        for (TravelAgency agency : agencyList) {
            if (agency.getEmail().equals(email) && agency.getPassword().equals(password)) {
                System.out.println("Welcome, " + agency.getAgencyName() + "\n");
                return;
            }
        }
        System.out.println("Login failed.\n");
    }

    public void saveData() {
        try (PrintWriter pw = new PrintWriter(fileName)) {
            for (RegisteredPassenger user : userList) {
                pw.println(user.getId() + "," + user.getName() + "," + user.getEmail() + "," + user.getPassword() + "," + user.getPhoneNumber() + "," + user.getGender() + "," + user.getBirthDate());
            }
            System.out.println("Data saved successfully.\n");
        } catch (Exception e) {
            System.out.println("Failed to save data: " + e.getMessage() + "\n");
        }
    }

    public void loadData() {
        try (Scanner fileScanner = new Scanner(new java.io.File(fileName))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",", -1);
                if (parts.length == 7) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String email = parts[2];
                    String password = parts[3];
                    String phoneNumber = parts[4];
                    String gender = parts[5];
                    String birthDate = parts[6];
                    RegisteredPassenger user = new RegisteredPassenger(id, name, email, password, phoneNumber, gender, birthDate);
                    userList.add(user);
                    if (id > this.id) {
                        this.id = id;
                    }
                }
            }
            System.out.println("Data loaded successfully\n");
        } catch (Exception e) {
            System.out.println("Failed to load data: " + e.getMessage() + "\n");
        }
    }

    public void saveAgencyData() {
        try (PrintWriter pw = new PrintWriter(agencyFileName)) {
            for (TravelAgency agency : agencyList) {
                pw.println(agency.getId() + "," + agency.getAgencyName() + "," + agency.getEmail() + "," + 
                           agency.getPassword() + "," + agency.getPhoneNumber());
            }
            System.out.println("Agency data saved successfully.\n");
        } catch (Exception e) {
            System.out.println("Failed to save agency data: " + e.getMessage() + "\n");
        }
    }

    public void loadAgencyData() {
        try {
            File file = new File(agencyFileName);
            
            // Create the file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("New agency data file created.\n");
                return;
            }
            
            try (Scanner fileScanner = new Scanner(file)) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split(",", -1);
                    if (parts.length == 5) {
                        int id = Integer.parseInt(parts[0]);
                        String agencyName = parts[1];
                        String email = parts[2];
                        String password = parts[3];
                        String phoneNumber = parts[4];
                        
                        TravelAgency agency = new TravelAgency(id, email, password, phoneNumber, agencyName);
                        agencyList.add(agency);
                        if (id > this.agencyId) {
                            this.agencyId = id;
                        }
                    }
                }
                System.out.println("Agency data loaded successfully.\n");
            }
        } catch (Exception e) {
            System.out.println("Failed to load agency data: " + e.getMessage() + "\n");
        }
    }
}
