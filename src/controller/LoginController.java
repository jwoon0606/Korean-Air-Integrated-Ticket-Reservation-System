package controller;
import user.RegisteredPassenger;
import user.TravelAgency;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/** 25.04.27 final modification :: PSY
* NJW(Creator), PMC, PSY
* Login business logic controller
* login() + isLoggedin(), logout() // Add
* signUp(RegisteredPassenger/TravelAgency) 
*/

public class LoginController {
    ArrayList<RegisteredPassenger> userList;
    ArrayList<TravelAgency> agencyList;
    private RegisteredPassenger currentUser = null; // To identify regular user login
    private TravelAgency currentAgency = null; // To identify travel agency login
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
    
    // 25.04.28 :: PSY - Method to check if a user or agency is logged in
    public boolean isLoggedIn() {
        return currentUser != null || currentAgency != null;
    }
    
    public RegisteredPassenger getCurrentUser() {
        return currentUser;
    }

    public TravelAgency getCurrentAgency() {
        return currentAgency;
    }
    

    public void signUp() {
        System.out.println("Sign Up Page");
        int id = ++this.id;
        System.out.print("name? "); String name = sc.nextLine();
        System.out.print("email? "); String email = sc.nextLine();
        
     // 25.04.28 :: PSY - Check if the email already exists in the userList
        for (RegisteredPassenger user : userList) {
            if (user.getEmail().equals(email)) {
                System.out.println("This email is already registered. Please use a different email.\n");
                return;
            }
        }
        
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
                currentUser = user; // 
                return;
            }
        }
        System.out.println("Login failed.\n");
    }
    
    // 25.04.28 :: PSY - logout by using current state values
    public void logout() {
        if (currentUser != null) {
            System.out.println("Logged out successfully, " + currentUser.getName() + ".\n");
            currentUser = null;  // Clear current user
        } else if (currentAgency != null) {
            System.out.println("Logged out successfully, " + currentAgency.getAgencyName() + ".\n");
            currentAgency = null;  // Clear current agency
        } else {
            System.out.println("No user or agency is logged in.\n");
        }
    }

    public void travelAgencSignUp() {
        System.out.println("Travel Agency Sign Up Page");
        int id = ++this.agencyId;
        System.out.print("Agency name? "); String agencyName = sc.nextLine();
        System.out.print("Email? "); String email = sc.nextLine();
        
     // 25.04.28 :: PSY - Check if the email already exists in the agencyList
        for (TravelAgency agency : agencyList) {
            if (agency.getEmail().equals(email)) {
                System.out.println("This email is already registered. Please use a different email.\n");
                return;
            }
        }
        
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
                System.out.println("Welcome, Agency: " + agency.getAgencyName() + "\n");
                currentAgency = agency;
                return;
            }
        }
        System.out.println("Login failed.\n");
    }
    
    // 25.04.28 :: PSY - Show User details
    public void displayUserInfo() {
        if (currentUser != null) {
            System.out.println("User Info:");
            System.out.println("Name: " + currentUser.getName());
            System.out.println("Email: " + currentUser.getEmail());
            System.out.println("Phone: " + currentUser.getPhoneNumber());
            System.out.println("Gender: " + currentUser.getGender());
            System.out.println("Birth Date: " + currentUser.getBirthDate() + "\n");
        } else if (currentAgency != null) {
            System.out.println("Travel Agency Info:");
            System.out.println("Agency Name: " + currentAgency.getAgencyName());
            System.out.println("Email: " + currentAgency.getEmail());
            System.out.println("Phone: " + currentAgency.getPhoneNumber() + "\n");
        } else {
            System.out.println("No user is logged in. \n");
        }
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
            System.out.println("Data loaded successfully.\n");
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
