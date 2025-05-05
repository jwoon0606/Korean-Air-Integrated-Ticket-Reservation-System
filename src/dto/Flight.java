package dto;

import java.util.List;

public class Flight {
    private String flightNumber;
    private String departure;
    private String destination;
    private String departureDate;
    private List<Seat> seats;

    // 생성자
    public Flight(String flightNumber, String departure, String destination, String departureDate, List<Seat> seats) {
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.destination = destination;
        this.departureDate = departureDate;
        this.seats = seats;
    }

    // getter, setter
    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Flight " + flightNumber + ": " + departure + " -> " + destination + " on " + departureDate;
    }
}
