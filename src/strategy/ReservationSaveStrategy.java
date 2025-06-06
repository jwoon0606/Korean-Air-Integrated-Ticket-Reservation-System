package strategy;
import dto.ReservationForm;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReservationSaveStrategy implements SaveStrategy<ReservationForm> {
    private static final String RESERVATION_FILE = "src/file/ReservationList.txt";

    @Override
    public void save(ReservationForm data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESERVATION_FILE, true))) {
            List<String> lines = data.toTextBlock(); // 각 Factory에서 정의한 저장 형식 유지
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.write("----");
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Failed to save reservation: " + e.getMessage());
        }
    }
}
