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
                System.out.println("환영합니다, 여행사: " + agency.getAgencyName() + "\n");
                currentAgency = agency;
                return true;
            }
        }
        System.out.println("로그인에 실패했습니다.\n");
        return false;
    }

    @Override
    public boolean register() {
        System.out.println("여행사 회원 가입 페이지");
        int newId = ++this.agencyId;
        System.out.print("여행사 이름? "); String agencyName = sc.nextLine();
        System.out.print("이메일? "); String email = sc.nextLine();
        
        // 이메일 중복 체크
        for (TravelAgency agency : agencyList) {
            if (agency.getEmail().equals(email)) {
                System.out.println("이미 등록된 이메일입니다. 다른 이메일을 사용하세요.\n");
                return false;
            }
        }
        
        System.out.print("비밀번호? "); String password = sc.nextLine();
        System.out.print("전화번호? "); String phoneNumber = sc.nextLine();

        TravelAgency newAgency = new TravelAgency(newId, email, password, phoneNumber, agencyName);
        agencyList.add(newAgency);
        System.out.println(newAgency.getAgencyName() + " 여행사 등록 완료");
        saveAgencyData();
        return true;
    }
    
    @Override
    public String getStrategyName() {
        return "여행사";
    }
    
    @Override
    public void displayUserInfo() {
        if (currentAgency != null) {
            System.out.println("여행사 정보:");
            System.out.println("여행사 이름: " + currentAgency.getAgencyName());
            System.out.println("이메일: " + currentAgency.getEmail());
            System.out.println("전화번호: " + currentAgency.getPhoneNumber() + "\n");
        } else {
            System.out.println("로그인된 여행사가 없습니다.\n");
        }
    }
    
    @Override
    public void logout() {
        if (currentAgency != null) {
            System.out.println("로그아웃 되었습니다, " + currentAgency.getAgencyName() + ".\n");
            currentAgency = null;
        } else {
            System.out.println("로그인된 여행사가 없습니다.\n");
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
    
    /**
     * 여행사 데이터 저장
     */
    public void saveAgencyData() {
        try (PrintWriter pw = new PrintWriter(agencyFileName)) {
            for (TravelAgency agency : agencyList) {
                pw.println(agency.getId() + "," + agency.getAgencyName() + "," + agency.getEmail() + "," + 
                           agency.getPassword() + "," + agency.getPhoneNumber());
            }
            System.out.println("여행사 데이터가 성공적으로 저장되었습니다.\n");
        } catch (Exception e) {
            System.out.println("여행사 데이터 저장 실패: " + e.getMessage() + "\n");
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
                System.out.println("새 여행사 데이터 파일이 생성되었습니다.\n");
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
                System.out.println("여행사 데이터가 성공적으로 로드되었습니다.\n");
            }
        } catch (Exception e) {
            System.out.println("여행사 데이터 로드 실패: " + e.getMessage() + "\n");
        }
    }
}