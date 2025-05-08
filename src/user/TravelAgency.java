package user;

public class TravelAgency extends User {
    private int id;
    private String password;
    private String agencyName;

    public TravelAgency(int id, String email, String password, String phoneNumber, String agencyName) {
        super(agencyName, email, phoneNumber);
        this.id = id;
        this.password = password;
        this.agencyName = agencyName;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAgencyName() { return agencyName; }
    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
        this.name = agencyName;  // 상위 클래스 name 필드와 동기화
    }
}
