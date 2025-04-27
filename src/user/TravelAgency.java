package user;

public class TravelAgency implements User {
    private int id;
    private String email;
    private String password;
    private String phoneNumber;
    private String agencyName;

    public TravelAgency(int id,  String email, String password, String phoneNumber, String agencyName) {
        this.id = id;

        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.agencyName = agencyName;
    }

    // agencyName을 class외부에서 접근할 수 있도록 getter를 통해 외부로 제공
    public String getAgencyName() {
        return agencyName;
    }

    // agencyName을 class외부에서 설정할 수 있도록 setter로 접근할 수 있게 함.
    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getEmail() {
       return email;
    }
    @Override
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String getPassword() {
       return password;
    }
    @Override
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }
    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getName() {
        return agencyName;
    }

    @Override
    public void setName(String name) {
        this.agencyName = name;
    }
}
