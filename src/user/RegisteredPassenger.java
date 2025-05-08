package user;

import dto.Mileage;

public class RegisteredPassenger extends User {
    private int id;
    private String password;
    private String gender;
    private String birthDay;
    private String passportNumber;
    private Mileage mileage;
    private String userGrade;
    private boolean deleted;

    public RegisteredPassenger(int id, String name, String email, String password,
                                String phoneNumber, String gender, String birthDay) {
        super(name, email, phoneNumber);
        this.id = id;
        this.password = password;
        this.gender = gender;
        this.birthDay = birthDay;
        this.deleted = false;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getBirthDay() { return birthDay; }
    public void setBirthDay(String birthDay) { this.birthDay = birthDay; }

    public String getPassportNumber() { return passportNumber; }
    public void setPassportNumber(String passportNumber) { this.passportNumber = passportNumber; }

    public Mileage getMileage() { return mileage; }
    public void setMileage(Mileage mileage) { this.mileage = mileage; }

    public String getUserGrade() { return userGrade; }
    public void setUserGrade(String userGrade) { this.userGrade = userGrade; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}
