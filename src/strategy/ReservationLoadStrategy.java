package strategy;

import dto.ReservationForm;
import factory.GuestReservationFactory;
import factory.RegisteredReservationFactory;
import factory.ReservationFactory;
import user.GuestPassenger;
import user.RegisteredPassenger;
import user.User;
import controller.LoginController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReservationLoadStrategy implements LoadStrategy {
    private final LoginController loginController;
    private static final String RESERVATION_FILE = "src/file/ReservationList.txt";

    public ReservationLoadStrategy(LoginController loginController) {
        this.loginController = loginController;
    }

    @Override
    public List<ReservationForm> load() {
        List<ReservationForm> reservations = new ArrayList<>();
        List<String> currentBlock = new ArrayList<>();

        User currentUser = loginController.getCurrentUser();
        ReservationFactory factory = resolveFactory(currentUser);

        try (BufferedReader reader = new BufferedReader(new FileReader(RESERVATION_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("----")) {
                    reservations.add(factory.load(currentBlock));
                    currentBlock.clear();
                } else {
                    currentBlock.add(line);
                }
            }

            if (!currentBlock.isEmpty()) {
                reservations.add(factory.load(currentBlock));
            }

        } catch (IOException e) {
            System.out.println("[오류] 예약 파일 읽기 실패: " + e.getMessage());
        }

        return reservations;
    }

    private ReservationFactory resolveFactory(User user) {
        if (user instanceof RegisteredPassenger registered) {
            return new RegisteredReservationFactory(registered);
        } else if (user instanceof GuestPassenger) {
            return new GuestReservationFactory();
        } else {
            throw new IllegalArgumentException("로그인된 사용자 유형을 알 수 없습니다.");
        }
    }
}
