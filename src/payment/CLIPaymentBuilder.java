package payment;

public class CLIPaymentBuilder implements PaymentBuilder {
    private Payment payment;

    public CLIPaymentBuilder() {
        payment = new Payment();
    }

    @Override
    public void setReservationId(String id) {
        payment.setReservationId(id);
    }

    @Override
    public void setPaymentMethod(String method) {
        payment.setPaymentMethod(method);
    }

    @Override
    public void setAmount(double amount) {
        payment.setAmount(amount);
    }

    @Override
    public void setOutputFormat() {
        payment.setOutputFormat("CLI");
    }

    @Override
    public Payment build() {
        return payment;
    }
}
