package ticket;

public class Ticket {
    private String reservationId;
    private String passengerName;
    private String flightNumber;
    private String seatNumber;
    private String outputFormat;

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    @Override
    public String toString() {
        return "[TICKET - " + outputFormat + "]\n" +
               "Reservation ID: " + reservationId + "\n" +
               "Passenger: " + passengerName + "\n" +
               "Flight: " + flightNumber + "\n" +
               "Seat: " + seatNumber;
    }
}
