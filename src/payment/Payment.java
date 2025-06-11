package payment;

public class Payment {
    private String reservationId;
    private String paymentMethod;
    private double amount;
    private String outputFormat;

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }
    
    public String getReservationId() { return reservationId; }
    public String getPaymentMethod() { return paymentMethod; }
    public double getAmount() { return amount; }
    public String getOutputFormat() { return outputFormat; }


    @Override
    public String toString() {
        return "[PAYMENT - " + outputFormat + "]\n"
             + "Reservation ID: " + reservationId + "\n"
             + "Method: " + paymentMethod + "\n"
             + "Amount Paid: $" + String.format("%.2f", amount);
    }
}
