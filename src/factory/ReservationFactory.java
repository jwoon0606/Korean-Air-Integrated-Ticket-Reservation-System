package factory;
import dto.ReservationForm;
import dto.ReservedFlight;
import java.util.*;

public abstract class ReservationFactory {

    // Operation method: Product를 생성하고 반환
	 public ReservationForm create(Scanner scanner, List<ReservedFlight> flights) {
	        System.out.println("[Factory] Starting reservation creation...");

	        if (flights == null || flights.isEmpty()) {
	            throw new IllegalArgumentException("[Error] At least one flight must be selected.");
	        }

	        ReservationForm form = createFromInput(scanner, flights);

	        // 이름 유효성 검사
	        if (form.getName() == null || form.getName().isBlank()) {
	            throw new IllegalStateException("[Error] Passenger name is missing.");
	        }

	        System.out.println("[Factory] Reservation created successfully for: " + form.getName());

	        return form;
	    }
	 
	 public ReservationForm load(List<String> lines) {
	        System.out.println("[Factory] Loading reservation from file...");

	        if (lines == null || lines.isEmpty()) {
	            throw new IllegalArgumentException("[Error] No data found to load reservation.");
	        }

	        ReservationForm form = createFromTextBlock(lines);

	        // 필수 필드 체크
	        if (form.getId() == null || form.getId().isBlank()) {
	            throw new IllegalStateException("[Error] Missing reservation ID in loaded data.");
	        }


	        System.out.println("[Factory] Reservation loaded successfully with ID: " + form.getId());

	        return form;
	    }

    // Factory method: 각 구체 클래스에서 구현
    protected abstract ReservationForm createFromInput(Scanner scanner, List<ReservedFlight> flights);

    protected abstract ReservationForm createFromTextBlock(List<String> lines);
}
