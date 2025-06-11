package payment;

public class PaymentDirector {
    private PaymentBuilder builder;

    public PaymentDirector(PaymentBuilder builder) {
        this.builder = builder;
    }

    public Payment construct(String reservationId, String method, double amount) {
        builder.setReservationId(reservationId);
        builder.setPaymentMethod(method);
        builder.setAmount(amount);
        builder.setOutputFormat();
        return builder.build();
    }
}
