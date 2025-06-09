package ticket;

import dto.ReservationForm;
import dto.ReservedFlight;
import dto.Flight;

import javax.swing.*;
import java.awt.*;

public class SwingTicketBuilder implements TicketBuilder {
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
            //ticket.setSeatNumber(rf.getCabinClass() + " (" + rf.getSeatCount() + "석)");
            ticket.setSeatNumber(generateRandomSeatNumber());
        } else {
            ticket.setFlightNumber("정보 없음");
            ticket.setSeatNumber("정보 없음");
        }
    }

    @Override
    public void setOutputFormat() {
        ticket.setOutputFormat("SWING");
    }

    @Override
    public Ticket getResult() {
        JFrame frame = new JFrame("Ticket Viewer");
        JTextArea area = new JTextArea(ticket.toString());
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        frame.add(area);
        frame.setSize(400, 300);
        frame.setVisible(true);
        return ticket;
    }
    
    private String generateRandomSeatNumber() {
        int row = (int) (Math.random() * 30) + 1; // 1~30
        char col = (char) ('A' + (int) (Math.random() * 6)); // A~F
        return row + String.valueOf(col); // e.g., "12C"
    }
    
}
