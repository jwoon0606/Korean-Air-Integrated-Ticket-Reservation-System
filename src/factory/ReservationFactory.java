package factory;
import dto.ReservationForm;
import dto.ReservedFlight;
import java.util.*;

public abstract class ReservationFactory {

    // Operation method: Product를 생성하고 반환
    public ReservationForm create(Scanner scanner, List<ReservedFlight> flights) {
        return createFromInput(scanner, flights);
    }

    public ReservationForm load(List<String> lines) {
        return createFromTextBlock(lines);
    }

    // Factory method: 각 구체 클래스에서 구현
    protected abstract ReservationForm createFromInput(Scanner scanner, List<ReservedFlight> flights);

    protected abstract ReservationForm createFromTextBlock(List<String> lines);
}
