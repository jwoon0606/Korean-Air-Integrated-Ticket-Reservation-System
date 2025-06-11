package payment;

public interface PaymentBuilder {
    void setReservationId(String id);
    void setPaymentMethod(String method);
    void setAmount(double amount);
    void setOutputFormat();
    Payment build();
}
