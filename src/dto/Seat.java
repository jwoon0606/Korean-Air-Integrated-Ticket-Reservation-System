package dto;

public class Seat {
    private String cabinClass;
    private int availableSeats;
    private double price;

    // 생성자
    public Seat(String cabinClass, int availableSeats, double price) {
        this.cabinClass = cabinClass;
        this.availableSeats = availableSeats;
        this.price = price;
    }

    // getter, setter
    public String getCabinClass() {
        return cabinClass;
    }

    public void setCabinClass(String cabinClass) {
        this.cabinClass = cabinClass;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // 좌석 정보 출력
    @Override
    public String toString() {
        return cabinClass + " Class - Available: " + availableSeats + " seats, Price: " + price + " USD";
    }
}
