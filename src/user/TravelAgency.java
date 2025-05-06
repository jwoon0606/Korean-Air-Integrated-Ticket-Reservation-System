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

    // Expose agencyName to external classes through getter method
    public String getAgencyName() {
        return agencyName;
    }

    // Allow setting agencyName from outside the class through setter method
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
