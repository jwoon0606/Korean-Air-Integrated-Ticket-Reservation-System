package dto;

import java.util.ArrayList;

public class ReservationForm {
    private String id;

    // 회원 정보
    private String name;
    private String gender;
    private String birthDate;
    private String carrierForMileageAccumulation;
    private String membershipNumber;

    // private String availableDiscount; // 개인 할인

    // contact information
    private String countryCode;
    private String mobileNumber;
    private String email;
    private String Language;

    // '승객 대신 예약 기능'도 추가 하면 좋을 듯

    private String registerGuestPassword;

    private ArrayList<Flight> flights;

    public ReservationForm(String id, String name, String gender, String birthDate, String carrierForMileageAccumulation, String membershipNumber, String countryCode, String mobileNumber, String email, String language, String registerGuestPassword, ArrayList<Flight> flights) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.carrierForMileageAccumulation = carrierForMileageAccumulation;
        this.membershipNumber = membershipNumber;
        this.countryCode = countryCode;
        this.mobileNumber = mobileNumber;
        this.email = email;
        Language = language;
        this.registerGuestPassword = registerGuestPassword;
        this.flights = flights;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCarrierForMileageAccumulation() {
        return carrierForMileageAccumulation;
    }

    public void setCarrierForMileageAccumulation(String carrierForMileageAccumulation) {
        this.carrierForMileageAccumulation = carrierForMileageAccumulation;
    }

    public String getMembershipNumber() {
        return membershipNumber;
    }

    public void setMembershipNumber(String membershipNumber) {
        this.membershipNumber = membershipNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getRegisterGuestPassword() {
        return registerGuestPassword;
    }

    public void setRegisterGuestPassword(String registerGuestPassword) {
        this.registerGuestPassword = registerGuestPassword;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public void setFlights(ArrayList<Flight> flights) {
        this.flights = flights;
    }
}
