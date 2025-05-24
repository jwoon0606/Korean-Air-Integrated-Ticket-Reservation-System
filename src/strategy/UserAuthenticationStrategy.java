package strategy;

import user.RegisteredPassenger;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 등록된 일반 사용자를 위한 인증 전략 구현체
 * 전략 패턴의 ConcreteStrategy 역할을 합니다.
 */
public class UserAuthenticationStrategy implements AuthenticationStrategy {
    private ArrayList<RegisteredPassenger> userList;
    private RegisteredPassenger currentUser = null;
    private final Scanner sc = new Scanner(System.in);
    private final String fileName = "src/file/UserList.txt";
    private int id = 0;
    private RegisteredPassenger lastRegisteredUser = null; // 새로 추가된 필드

    /**
     * 생성자: 사용자 데이터 로드
     */
    public UserAuthenticationStrategy() {
        this.userList = new ArrayList<>();
        loadData();
    }

    @Override
    public boolean authenticate(String email, String password) {
        for (RegisteredPassenger user : userList) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                System.out.println("Welcome, " + user.getName() + "\n");
                currentUser = user;
                return true;
            }
        }
        System.out.println("Login failed.\n");
        return false;
    }

    @Override
    public boolean register() {
        System.out.println("Sign up page");
        this.lastRegisteredUser = null; // 메서드 시작 시 초기화
        int newId = ++this.id;
        System.out.print("name? "); String name = sc.nextLine();
        System.out.print("email? "); String email = sc.nextLine();
        
        // 이메일 중복 체크
        for (RegisteredPassenger user : userList) {
            if (user.getEmail().equals(email)) {
                System.out.println("This email is already registered. Please use another email.\\\\n");
                return false; // 실패 시 false 반환
            }
        }
        
        System.out.print("password? "); String password = sc.nextLine();
        System.out.print("phone number? "); String phoneNumber = sc.nextLine();
        System.out.print("gender? "); String gender = sc.nextLine();
        System.out.print("birth date? "); String birthDate = sc.nextLine();

        RegisteredPassenger newUser = new RegisteredPassenger(newId, name, email, password, phoneNumber, gender, birthDate);
        userList.add(newUser);
        System.out.println(newUser.getName() + " registered successfully");
        saveData();
        this.lastRegisteredUser = newUser; // 성공 시 객체 저장
        return true; // 성공 시 true 반환
    }

    /**
     * 마지막으로 가입 성공한 사용자 정보를 반환합니다.
     * @return 마지막 가입 사용자 객체, 없거나 실패 시 null
     */
    public RegisteredPassenger getLastRegisteredUser() {
        return this.lastRegisteredUser;
    }

    @Override
    public String getStrategyName() {
        return "General user";
    }
    
    @Override
    public void displayUserInfo() {
        if (currentUser != null) {
            System.out.println("User information:");
            System.out.println("Name: " + currentUser.getName());
            System.out.println("Email: " + currentUser.getEmail());
            System.out.println("Phone number: " + currentUser.getPhoneNumber());
            System.out.println("Gender: " + currentUser.getGender());
            System.out.println("Birth date: " + currentUser.getBirthDay() + "\n");
        } else {
            System.out.println("No user is logged in.\n");
        }
    }
    
    @Override
    public void logout() {
        if (currentUser != null) {
            System.out.println("Logged out successfully, " + currentUser.getName() + ".\n");
            currentUser = null;
        } else {
            System.out.println("No user is logged in.\n");
        }
    }
    
    @Override
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    /**
     * 현재 로그인된 사용자 반환
     */
    public RegisteredPassenger getCurrentUser() {
        return currentUser;
    }
    
    @Override
    public Object getCurrentUserObject() {
        return currentUser;
    }
    
    /**
     * 사용자 데이터 저장
     */
    public void saveData() {
        try (PrintWriter pw = new PrintWriter(fileName)) {
            for (RegisteredPassenger user : userList) {
                pw.println(user.getId() + "," + user.getName() + "," + user.getEmail() + "," + 
                           user.getPassword() + "," + user.getPhoneNumber() + "," + 
                           user.getGender() + "," + user.getBirthDay());
            }
            System.out.println("Data saved successfully.\n");
        } catch (Exception e) {
            System.out.println("Data saving failed: " + e.getMessage() + "\n");
        }
    }

    /**
     * 사용자 데이터 로드
     */
    private void loadData() {
        try {
            File file = new File(fileName);
            
            // 파일이 없으면 생성
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // 폴더가 없으면 생성
                file.createNewFile();
                System.out.println("New user data file created.\n");
                return;
            }
            
            try (Scanner fileScanner = new Scanner(file)) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split(",", -1);
                    if (parts.length == 7) {
                        int userId = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        String email = parts[2];
                        String password = parts[3];
                        String phoneNumber = parts[4];
                        String gender = parts[5];
                        String birthDate = parts[6];
                        RegisteredPassenger user = new RegisteredPassenger(userId, name, email, password, phoneNumber, gender, birthDate);
                        userList.add(user);
                        if (userId > this.id) {
                            this.id = userId;
                        }
                    }
                }
                System.out.println("User data loaded successfully.\n");
            }
        } catch (Exception e) {
            System.out.println("Data loading failed: " + e.getMessage() + "\n");
        }
    }

    public boolean deleteUser(String email) {
        for (RegisteredPassenger user : userList) {
            if (user.getEmail().equals(email)) {
                userList.remove(user);
                System.out.println("User " + email + " deleted successfully.\n");
                saveData();
                return true;
            }
        }
        System.out.println("User " + email + " not found.\n");
        return false;
    }
}