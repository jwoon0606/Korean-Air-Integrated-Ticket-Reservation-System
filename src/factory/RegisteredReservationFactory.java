package factory;

import dto.*;
import user.RegisteredPassenger;

import java.util.*;
import java.util.function.BiConsumer;

public class RegisteredReservationFactory extends ReservationFactory {

    private final RegisteredPassenger user;

    public RegisteredReservationFactory(RegisteredPassenger user) {
        this.user = user;
    }

    @Override
    protected ReservationForm createFromInput(Scanner scanner, List<ReservedFlight> flights) {
        System.out.println("\n[Registered Passenger Reservation Details Input]");

        String id = UUID.randomUUID().toString();

        System.out.print("Preferred Language: ");
        String language = scanner.nextLine();

        System.out.print("Mileage Airline: ");
        String mileageAirline = scanner.nextLine();

        String membershipNumber = user.getMileage() != null
                ? user.getMileage().getMembershipNumber()
                : UUID.randomUUID().toString().substring(0, 8); // fallback

        return new ReservationForm(
                id,
                user.getName(),
                user.getGender(),
                user.getBirthDay(),
                mileageAirline,
                membershipNumber,
                user.getPhoneNumber(),
                user.getEmail(),
                language,
                null, // guest password 없음
                new ArrayList<>(flights)
        );
    }

    @Override
    protected ReservationForm createFromTextBlock(List<String> lines) {
        ReservationForm form = new ReservationForm();
        List<ReservedFlight> flights = new ArrayList<>();

        Map<String, BiConsumer<String, ReservationForm>> handlers = new LinkedHashMap<>();

        handlers.put("Reservation ID: ", (line, f) -> f.setId(extractValue(line)));
        handlers.put("Passenger Name: ", (line, f) -> f.setName(extractValue(line)));
        handlers.put("Birth Date: ", (line, f) -> f.setBirthDate(extractValue(line)));
        handlers.put("Gender: ", (line, f) -> f.setGender(extractValue(line)));
        handlers.put("Contact: ", (line, f) -> {
            String[] parts = extractValue(line).split(", ");
            f.setMobileNumber(parts[0].trim());
            if (parts.length > 1) {
                f.setEmail(parts[1].trim());
            }
        });
        handlers.put("Language: ", (line, f) -> f.setLanguage(extractValue(line)));
        handlers.put("Mileage: ", (line, f) -> f.setCarrierForMileageAccumulation(extractValue(line)));
        handlers.put("Membership Number: ", (line, f) -> f.setMembershipNumber(extractValue(line)));

        for (String line : lines) {
            boolean handled = false;
            for (Map.Entry<String, BiConsumer<String, ReservationForm>> entry : handlers.entrySet()) {
                if (line.startsWith(entry.getKey())) {
                    entry.getValue().accept(line, form);
                    handled = true;
                    break;
                }
            }

            if (!handled) {
                if (line.startsWith("Flight: ")) {
                    String[] parts = extractValue(line).split(", ");
                    if (parts.length >= 3) {
                        String[] route = parts[2].split(" → ");
                        Flight flight = new Flight(parts[0], route[0].trim(), route[1].trim(), parts[1], new ArrayList<>());
                        ReservedFlight rf = new ReservedFlight();
                        rf.setFlight(flight);
                        flights.add(rf);
                    }
                } else if (line.startsWith("Class: ")) {
                    if (!flights.isEmpty()) {
                        ReservedFlight last = flights.get(flights.size() - 1);
                        String[] seatParts = line.split(", ");
                        last.setCabinClass(seatParts[0].split(": ")[1].trim());
                        last.setSeatCount(Integer.parseInt(seatParts[1].split(": ")[1].trim()));
                    }
                }
            }
        }

        form.setReservedFlights(new ArrayList<>(flights));
        return form;
    }

    private String extractValue(String line) {
        return line.substring(line.indexOf(": ") + 2).trim();
    }
}
