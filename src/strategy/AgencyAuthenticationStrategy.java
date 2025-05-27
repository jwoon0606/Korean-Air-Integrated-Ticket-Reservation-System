package strategy;

import user.TravelAgency;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class AgencyAuthenticationStrategy implements AuthenticationStrategy {
    private ArrayList<TravelAgency> agencyList;
    private TravelAgency currentAgency = null;
    private final Scanner sc = new Scanner(System.in);
    private final String agencyFileName = "src/file/AgencyList.txt";
    private int agencyId = 0;
    private TravelAgency lastRegisteredAgency = null;

    public AgencyAuthenticationStrategy() {
        this.agencyList = new ArrayList<>();
        loadAgencyData();
    }

    @Override
    public boolean authenticate(String email, String password) {
        for (TravelAgency agency : agencyList) {
            if (agency.getEmail().equals(email) && agency.getPassword().equals(password)) {
                System.out.println("Welcome, " + agency.getAgencyName() + "\n");
                currentAgency = agency;
                return true;
            }
        }
        System.out.println("Login failed.\n");
        return false;
    }

    @Override
    public boolean register() {
        System.out.println("Agency sign up page");
        this.lastRegisteredAgency = null;
        int newId = ++this.agencyId;
        System.out.print("Agency name? "); String agencyName = sc.nextLine();
        System.out.print("Email? "); String email = sc.nextLine();
        
        for (TravelAgency agency : agencyList) {
            if (agency.getEmail().equals(email)) {
                System.out.println("This email is already registered. Please use another email.\n");
                return false;
            }
        }
        
        System.out.print("Password? "); String password = sc.nextLine();
        System.out.print("Phone number? "); String phoneNumber = sc.nextLine();

        TravelAgency newAgency = new TravelAgency(newId, email, password, phoneNumber, agencyName);
        agencyList.add(newAgency);
        System.out.println(newAgency.getAgencyName() + " registered successfully");
        saveAgencyData();
        this.lastRegisteredAgency = newAgency;
        return true;
    }

    @Override
    public Object getLastRegisteredUser() {
        return this.lastRegisteredAgency;
    }

    @Override
    public boolean deleteUser(String email) {
        TravelAgency agencyToRemove = null;
        for (TravelAgency agency : agencyList) {
            if (agency.getEmail().equals(email)) {
                agencyToRemove = agency;
                break;
            }
        }
        if (agencyToRemove != null) {
            agencyList.remove(agencyToRemove);
            System.out.println("Agency " + email + " deleted successfully.\n");
            saveAgencyData();
            return true;
        }
        System.out.println("Agency " + email + " not found.\n");
        return false;
    }
    
    @Override
    public String getStrategyName() {
        return "Travel agency";
    }
    
    @Override
    public void displayUserInfo() {
        if (currentAgency != null) {
            System.out.println("Agency information:");
            System.out.println("Agency name: " + currentAgency.getAgencyName());
            System.out.println("Email: " + currentAgency.getEmail());
            System.out.println("Phone number: " + currentAgency.getPhoneNumber() + "\n");
        } else {
            System.out.println("No agency is logged in.\n");
        }
    }
    
    @Override
    public void logout() {
        if (currentAgency != null) {
            System.out.print("\nLogged out successfully, " + currentAgency.getAgencyName() + ".");
            currentAgency = null;
        } else {
            System.out.println("No agency is logged in.\n");
        }
    }
    
    @Override
    public boolean isLoggedIn() {
        return currentAgency != null;
    }
    
    @Override
    public Object getCurrentUserObject() {
        return currentAgency;
    }
    
    public void saveAgencyData() {
        try (PrintWriter pw = new PrintWriter(agencyFileName)) {
            for (TravelAgency agency : agencyList) {
                pw.println(agency.getId() + "," + agency.getAgencyName() + "," + agency.getEmail() + "," + 
                           agency.getPassword() + "," + agency.getPhoneNumber());
            }
            System.out.println("Agency data saved successfully.");
        } catch (Exception e) {
            System.out.println("Agency data saving failed: " + e.getMessage() + "\n");
        }
    }

    private void loadAgencyData() {
        try {
            File file = new File(agencyFileName);
            
            if (!file.exists()) {
                file.getParentFile().mkdirs();
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
            System.out.println("Agency data loading failed: " + e.getMessage() + "\n");
        }
    }
}