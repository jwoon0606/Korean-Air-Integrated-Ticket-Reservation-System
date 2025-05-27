package strategy;

import config.Constants;
import dto.ReservationForm;
import dto.ReservationFormFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReservationLoadStrategy implements LoadStrategy{
    @Override
    public List<ReservationForm> load() {
        List<ReservationForm> reservations = new ArrayList<>();
        List<String> currentBlock = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.RESERVATION_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("----")) {
                    reservations.add(ReservationFormFactory.fromTextBlock(currentBlock));
                    currentBlock.clear();
                } else {
                    currentBlock.add(line);
                }
            }
            if (!currentBlock.isEmpty()) {
                reservations.add(ReservationFormFactory.fromTextBlock(currentBlock));
            }
        } catch (IOException e) {
            System.out.println("[오류] 예약 파일 읽기 실패: " + e.getMessage());
        }
        return reservations;
    }
}
