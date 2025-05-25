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
    private String language;

    // '승객 대신 예약 기능'도 추가 하면 좋을 듯

    private String registerGuestPassword;
    private ArrayList<ReservedFlight> reservedFlights;

    /*클래스의 기본 생성자(no-argument constructor): 빈 ReservationForm 객체를 먼저 생성한 다음,
    라인을 하나씩 파싱하면서 setXxx() 메서드를 호출해 필드 값을 설정하기 때문 */
    public ReservationForm() {}  
    
    
    public ReservationForm(String id, String name, String gender, String birthDate, String carrierForMileageAccumulation, String membershipNumber, String countryCode, String mobileNumber, String email, String language, String registerGuestPassword, ArrayList<ReservedFlight> reservedFlights) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.carrierForMileageAccumulation = carrierForMileageAccumulation;
        this.membershipNumber = membershipNumber;
        this.countryCode = countryCode;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.language = language;
        this.registerGuestPassword = registerGuestPassword;
        this.reservedFlights = reservedFlights;
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
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRegisterGuestPassword() {
        return registerGuestPassword;
    }

    public void setRegisterGuestPassword(String registerGuestPassword) {
        this.registerGuestPassword = registerGuestPassword;
    }

    public ArrayList<ReservedFlight> getReservedFlights() {
        return reservedFlights;
    }

    public void setReservedFlightsFlights(ArrayList<ReservedFlight> reservedFlights) {
        this.reservedFlights = reservedFlights;
    }
    
    /**
     * ReservationForm 객체의 정보를 파일에 저장할 형식의 문자열 리스트로 변환합니다.
     * 이 메소드는 ReservationController의 overwriteAllReservations에서 사용되며,
     * ReservationFormFactory.fromTextBlock이 파싱하는 형식과 동일해야 합니다.
     * @return 파일 저장 형식의 문자열 리스트
     */
    public ArrayList<String> toTextBlock() {
        ArrayList<String> block = new ArrayList<>();

        // 각 필드의 값이 null이 아닌 경우에만 문자열로 변환하여 리스트에 추가합니다.
        // ReservationFormFactory의 fromTextBlock 메소드에서 사용하는 키 값과 형식을 일치시킵니다.

        if (id != null) block.add("Reservation ID: " + id);
        if (name != null) block.add("Passenger Name: " + name);
        if (birthDate != null) block.add("Birth Date: " + birthDate);
        if (gender != null) block.add("Gender: " + gender);
        // Contact 정보는 countryCode, mobileNumber, email이 모두 존재할 때 하나의 라인으로 만듭니다.
        if (countryCode != null && mobileNumber != null && email != null) {
            block.add("Contact: +" + countryCode + " " + mobileNumber + ", " + email);
        }
        if (language != null) block.add("Language: " + language);
        if (registerGuestPassword != null) block.add("Guest Password: " + registerGuestPassword);

        // ReservedFlights 정보 처리
        if (reservedFlights != null) {
            for (ReservedFlight rf : reservedFlights) {
                Flight flight = rf.getFlight();
                if (flight != null) {
                    // Flight 정보: FlightNumber, DepartureDate, Departure → Destination 형식
                    block.add("Flight: " + flight.getFlightNumber() + ", " + 
                              flight.getDepartureDate() + ", " + 
                              flight.getDeparture() + " → " + flight.getDestination());
                }
                // Class 정보: CabinClass, Seats Reserved 형식
                if (rf.getCabinClass() != null) { // seatCount는 int형이므로 null 체크 불필요
                    block.add("Class: " + rf.getCabinClass() + ", Seats Reserved: " + rf.getSeatCount());
                }
            }
        }
        return block;
    }

    // ReservationForm에서 예약 정보를 출력할 때 ReservedFlight 객체를 자동으로 출력
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reservation ID: ").append(id).append("\n")
          .append("Name: ").append(name).append("\n")
          .append("Email: ").append(email).append("\n")
          .append("Language: ").append(language).append("\n")
          .append("Flights:\n");
        for (ReservedFlight flight : reservedFlights) {
            sb.append(flight.toString()).append("\n");
        }
        return sb.toString();
    }
}
