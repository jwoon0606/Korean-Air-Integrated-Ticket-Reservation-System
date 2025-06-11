package payment;

import javax.swing.*;
import java.awt.*;

public class SwingPaymentBuilder implements PaymentBuilder {
    private Payment payment;

    public SwingPaymentBuilder() {
        this.payment = new Payment();
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
        payment.setOutputFormat("SWING");
    }

    @Override
    public Payment build() {
        showPaymentInFrame(payment);
        return payment;
    }

    private void showPaymentInFrame(Payment payment) {
        JFrame frame = new JFrame("Payment Receipt");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLocationRelativeTo(null); // 화면 중앙

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        area.setText(
            "====== Payment Receipt ======\n" +
            "Reservation ID : " + payment.getReservationId() + "\n" +
            "Payment Method : " + payment.getPaymentMethod() + "\n" +
            "Amount Paid    : $" + String.format("%.2f", payment.getAmount()) + "\n"
        );

        frame.getContentPane().add(new JScrollPane(area), BorderLayout.CENTER);

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> frame.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeBtn);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
