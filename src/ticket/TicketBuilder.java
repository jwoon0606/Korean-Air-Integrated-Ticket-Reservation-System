package ticket;

import dto.ReservationForm;

public interface TicketBuilder {
    void reset();
    void setReservationData(ReservationForm form);
    void setOutputFormat();
    Ticket getResult();
}
