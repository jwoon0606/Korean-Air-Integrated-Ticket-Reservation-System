package factory;

import dto.Flight;
import dto.ReservationForm;
import dto.ReservedFlight;

import java.util.*;

public class GuestReservationFactory extends ReservationFactory {

    @FunctionalInterface
    interface LineHandler {
        void handle(String line, ReservationForm form, List<ReservedFlight> flights);
    }

    private static final Map<String, LineHandler> handlers = new LinkedHashMap<>();

    static {
        handlers.put("Reservation ID: ", (line, form, flights) -> 
            form.setId(line.split(": ")[1]));

        handlers.put("Passenger Name: ", (line, form, flights) -> 
            form.setName(line.split(": ")[1]));

        handlers.put("Birth Date: ", (line, form, flights) -> 
            form.setBirthDate(line.split(": ")[1]));

        handlers.put("Gender: ", (line, form, flights) -> 
            form.setGender(line.split(": ")[1]));

        handlers.put("Contact: ", (line, form, flights) -> {
            String[] parts = line.split(": ", 2);
            if (parts.length > 1) {
                String[] contact = parts[1].split(", ");
                if (contact.length >= 2) {
                    form.setMobileNumber(contact[0].trim());
                    form.setEmail(contact[1].trim());
                }
            }
        });


        handlers.put("Language: ", (line, form, flights) -> 
            form.setLanguage(line.split(": ")[1]));

        handlers.put("Guest Password: ", (line, form, flights) -> 
            form.setRegisterGuestPassword(line.split(": ")[1]));

        handlers.put("Mileage: ", (line, form, flights) -> 
            form.setCarrierForMileageAccumulation(line.split(": ")[1]));

        handlers.put("Membership Number: ", (line, form, flights) -> 
            form.setMembershipNumber(line.split(": ")[1]));

        handlers.put("Flight: ", (line, form, flights) -> {
            String[] parts = line.split(": ")[1].split(", ");
            String[] route = parts[2].split(" → ");
            Flight flight = new Flight(parts[0], route[0].trim(), route[1].trim(), parts[1], new ArrayList<>());
            ReservedFlight rf = new ReservedFlight();
            rf.setFlight(flight);
            flights.add(rf);
        });

        handlers.put("Class: ", (line, form, flights) -> {
            ReservedFlight last = flights.get(flights.size() - 1);
            String[] seatParts = line.split(", ");
            last.setCabinClass(seatParts[0].split(": ")[1]);
            last.setSeatCount(Integer.parseInt(seatParts[1].split(": ")[1]));
        });
    }

    @Override
    protected ReservationForm createFromInput(Scanner scanner, List<ReservedFlight> flights) {
        System.out.println("\n[Guest Reservation Input]");
        String id = UUID.randomUUID().toString();

        System.out.print("Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Gender (M/F): ");
        String gender = scanner.nextLine();

        System.out.print("Birth Date (YYYY-MM-DD): ");
        String birthDate = scanner.nextLine();

        System.out.print("Mileage Airline (optional): ");
        String mileageAirline = scanner.nextLine();

        System.out.print("Membership Number (optional): ");
        String membershipNumber = scanner.nextLine();

        System.out.print("Mobile Number: ");
        String mobileNumber = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Preferred Language: ");
        String language = scanner.nextLine();

        System.out.print("Guest Password: ");
        String guestPassword = scanner.nextLine();

        return new ReservationForm(
                id, name, gender, birthDate,
                mileageAirline, membershipNumber,
                mobileNumber, email, language,
                guestPassword, new ArrayList<>(flights)
        );
    }

    @Override
    protected ReservationForm createFromTextBlock(List<String> lines) {
        ReservationForm form = new ReservationForm();
        List<ReservedFlight> flights = new ArrayList<>();

        for (String line : lines) {
            for (Map.Entry<String, LineHandler> entry : handlers.entrySet()) {
                if (line.startsWith(entry.getKey())) {
                    entry.getValue().handle(line, form, flights);
                    break;
                }
            }
        }

        form.setReservedFlights(new ArrayList<>(flights));
        return form;
    }
}
