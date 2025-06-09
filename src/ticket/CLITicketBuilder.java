package ticket;

import dto.ReservationForm;
import dto.ReservedFlight;
import dto.Flight;

public class CLITicketBuilder implements TicketBuilder {
    private Ticket ticket;

    @Override
    public void reset() {
        ticket = new Ticket();
    }

    @Override
    public void setReservationData(ReservationForm form) {
        ticket.setReservationId(form.getId());
        ticket.setPassengerName(form.getName());

        if (form.getReservedFlights() != null && !form.getReservedFlights().isEmpty()) {
            ReservedFlight rf = form.getReservedFlights().get(0);
            Flight f = rf.getFlight();
            ticket.setFlightNumber(f.getFlightNumber());
         // 실제 좌석 대신 랜덤 생성
            ticket.setSeatNumber(generateRandomSeatNumber());
            //ticket.setSeatNumber(rf.getCabinClass() + " (" + rf.getSeatCount() + "석)");
        } else {
            ticket.setFlightNumber("정보 없음");
            ticket.setSeatNumber("정보 없음");
        }
    }

    @Override
    public void setOutputFormat() {
        ticket.setOutputFormat("CLI");
    }

    @Override
    public Ticket getResult() {
        return ticket;
    }
    
    private String generateRandomSeatNumber() {
        int row = (int) (Math.random() * 30) + 1; // 1~30
        char col = (char) ('A' + (int) (Math.random() * 6)); // A~F
        return row + String.valueOf(col); // e.g., "12C"
    }
    
}
