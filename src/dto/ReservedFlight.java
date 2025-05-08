package dto;

public class ReservedFlight {
    private Flight flight;
    private String cabinClass;
    private int seatCount;

    public ReservedFlight(Flight flight, String cabinClass, int seatCount) {
        this.flight = flight;
        this.cabinClass = cabinClass;
        this.seatCount = seatCount;
    }

    public ReservedFlight() {

    }

    public Flight getFlight() {
        return flight;
    }

    public String getCabinClass() {
        return cabinClass;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public void setCabinClass(String cabinClass) {
        this.cabinClass = cabinClass;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }
    
 // 예약된 항공편 정보를 예약조회에서 출력하는 toString 메서드
    @Override
    public String toString() {
        return flight.toString() + ", Cabin Class: " + cabinClass + ", Seats Reserved: " + seatCount;
    }
    
}
