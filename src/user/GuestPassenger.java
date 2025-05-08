package user;

public class GuestPassenger extends User {
    private int id;
    private String gender;
    private String birthDay;
    private String partnerAirline;
    private String partnerAirlineMemberID;
    private String reservationNumber;
    private String password;

    public GuestPassenger() {
        super("Guest", "guest@unknown.com", "000-0000-0000");
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getBirthDay() { return birthDay; }
    public void setBirthDay(String birthDay) { this.birthDay = birthDay; }

    public String getPartnerAirline() { return partnerAirline; }
    public void setPartnerAirline(String partnerAirline) { this.partnerAirline = partnerAirline; }

    public String getPartnerAirlineMemberID() { return partnerAirlineMemberID; }
    public void setPartnerAirlineMemberID(String partnerAirlineMemberID) { this.partnerAirlineMemberID = partnerAirlineMemberID; }

    public String getReservationNumber() { return reservationNumber; }
    public void setReservationNumber(String reservationNumber) { this.reservationNumber = reservationNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }


}
