package ticket;

import dto.ReservationForm;

public class TicketIssuer {
    private TicketBuilder builder;

    public TicketIssuer(TicketBuilder builder) {
        this.builder = builder;
    }

    public Ticket issue(ReservationForm form) {
        builder.reset();
        builder.setReservationData(form);
        builder.setOutputFormat();
        return builder.getResult();
    }
}
