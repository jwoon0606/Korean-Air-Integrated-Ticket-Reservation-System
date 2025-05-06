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
                System.out.println("환영합니다, " + user.getName() + "\n");
                currentUser = user;
                return true;
            }
        }
        System.out.println("로그인에 실패했습니다.\n");
        return false;
    }

    @Override
    public boolean register() {
        System.out.println("회원 가입 페이지");
        int newId = ++this.id;
        System.out.print("이름? "); String name = sc.nextLine();
        System.out.print("이메일? "); String email = sc.nextLine();
        
        // 이메일 중복 체크
        for (RegisteredPassenger user : userList) {
            if (user.getEmail().equals(email)) {
                System.out.println("이미 등록된 이메일입니다. 다른 이메일을 사용하세요.\n");
                return false;
            }
        }
        
        System.out.print("비밀번호? "); String password = sc.nextLine();
        System.out.print("전화번호? "); String phoneNumber = sc.nextLine();
        System.out.print("성별? "); String gender = sc.nextLine();
        System.out.print("생년월일? "); String birthDate = sc.nextLine();

        RegisteredPassenger newUser = new RegisteredPassenger(newId, name, email, password, phoneNumber, gender, birthDate);
        userList.add(newUser);
        System.out.println(newUser.getName() + " 등록 완료");
        saveData();
        return true;
    }

    @Override
    public String getStrategyName() {
        return "일반 회원";
    }
    
    @Override
    public void displayUserInfo() {
        if (currentUser != null) {
            System.out.println("회원 정보:");
            System.out.println("이름: " + currentUser.getName());
            System.out.println("이메일: " + currentUser.getEmail());
            System.out.println("전화번호: " + currentUser.getPhoneNumber());
            System.out.println("성별: " + currentUser.getGender());
            System.out.println("생년월일: " + currentUser.getBirthDate() + "\n");
        } else {
            System.out.println("로그인된 사용자가 없습니다.\n");
        }
    }
    
    @Override
    public void logout() {
        if (currentUser != null) {
            System.out.println("로그아웃 되었습니다, " + currentUser.getName() + "님.\n");
            currentUser = null;
        } else {
            System.out.println("로그인된 사용자가 없습니다.\n");
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
    
    /**
     * 사용자 데이터 저장
     */
    public void saveData() {
        try (PrintWriter pw = new PrintWriter(fileName)) {
            for (RegisteredPassenger user : userList) {
                pw.println(user.getId() + "," + user.getName() + "," + user.getEmail() + "," + 
                           user.getPassword() + "," + user.getPhoneNumber() + "," + 
                           user.getGender() + "," + user.getBirthDate());
            }
            System.out.println("데이터가 성공적으로 저장되었습니다.\n");
        } catch (Exception e) {
            System.out.println("데이터 저장 실패: " + e.getMessage() + "\n");
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
                System.out.println("새 사용자 데이터 파일이 생성되었습니다.\n");
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
                System.out.println("사용자 데이터가 성공적으로 로드되었습니다.\n");
            }
        } catch (Exception e) {
            System.out.println("데이터 로드 실패: " + e.getMessage() + "\n");
        }
    }
}