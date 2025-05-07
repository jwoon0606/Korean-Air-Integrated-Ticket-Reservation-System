package strategy;

import user.TravelAgency;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 여행사 사용자를 위한 인증 전략 구현체
 * 전략 패턴의 ConcreteStrategy 역할을 합니다.
 */
public class AgencyAuthenticationStrategy implements AuthenticationStrategy {
    private ArrayList<TravelAgency> agencyList;
    private TravelAgency currentAgency = null;
    private final Scanner sc = new Scanner(System.in);
    private final String agencyFileName = "src/file/AgencyList.txt";
    private int agencyId = 0;

    /**
     * 생성자: 여행사 데이터 로드
     */
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
        int newId = ++this.agencyId;
        System.out.print("Agency name? "); String agencyName = sc.nextLine();
        System.out.print("Email? "); String email = sc.nextLine();
        
        // 이메일 중복 체크
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
        return true;
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
            System.out.println("Logged out successfully, " + currentAgency.getAgencyName() + ".\n");
            currentAgency = null;
        } else {
            System.out.println("No agency is logged in.\n");
        }
    }
    
    @Override
    public boolean isLoggedIn() {
        return currentAgency != null;
    }
    
    /**
     * 현재 로그인된 여행사 반환
     */
    public TravelAgency getCurrentAgency() {
        return currentAgency;
    }
    
    @Override
    public Object getCurrentUserObject() {
        return currentAgency;
    }
    
    /**
     * 여행사 데이터 저장
     */
    public void saveAgencyData() {
        try (PrintWriter pw = new PrintWriter(agencyFileName)) {
            for (TravelAgency agency : agencyList) {
                pw.println(agency.getId() + "," + agency.getAgencyName() + "," + agency.getEmail() + "," + 
                           agency.getPassword() + "," + agency.getPhoneNumber());
            }
            System.out.println("Agency data saved successfully.\n");
        } catch (Exception e) {
            System.out.println("Agency data saving failed: " + e.getMessage() + "\n");
        }
    }

    /**
     * 여행사 데이터 로드
     */
    private void loadAgencyData() {
        try {
            File file = new File(agencyFileName);
            
            // 파일이 없으면 생성
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // 폴더가 없으면 생성
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