package payment;

import java.util.Scanner;

public class PaymentProcessor {
    private final Scanner scanner = new Scanner(System.in);

    public void processPayment(String reservationId, double totalPrice) {
        System.out.print("Select payment method (Card/Bank/PayPal): ");
        String method = scanner.nextLine();

        System.out.print("Select display format (1. CLI, 2. Swing): ");
        int formatChoice = Integer.parseInt(scanner.nextLine());

        PaymentBuilder builder;
        if (formatChoice == 2) {
            builder = new SwingPaymentBuilder();
        } else {
            builder = new CLIPaymentBuilder();
        }

        PaymentDirector director = new PaymentDirector(builder);
        Payment payment = director.construct(reservationId, method, totalPrice);

        // CLI 모드에서는 직접 출력해줘야 함
        if (payment.getOutputFormat().equals("CLI")) {
            System.out.println(payment);
        }
    }
}
