package user;

public class GuestPassenger implements User{
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private String gender;
    private String birthDay;

    private String partnerAirline;
    private String partnerAirlineMemberID;
    private String reservationNumber;
    private String password;

    public double getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getPartnerAirline() {
        return partnerAirline;
    }

    public void setPartnerAirline(String partnerAirline) {
        this.partnerAirline = partnerAirline;
    }

    public String getPartnerAirlineMemberID() {
        return partnerAirlineMemberID;
    }

    public void setPartnerAirlineMemberID(String partnerAirlineMemberID) {
        this.partnerAirlineMemberID = partnerAirlineMemberID;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
