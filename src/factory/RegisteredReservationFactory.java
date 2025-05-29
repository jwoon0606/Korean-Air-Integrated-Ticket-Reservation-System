package factory;

import dto.*;
import user.RegisteredPassenger;

import java.util.*;

public class RegisteredReservationFactory extends ReservationFactory {

    private final RegisteredPassenger user;

    public RegisteredReservationFactory(RegisteredPassenger user) {
        this.user = user;
    }

    @Override
    protected ReservationForm createFromInput(Scanner scanner, List<ReservedFlight> flights) {
        System.out.println("\n[Registered User Reservation]");

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

        for (String line : lines) {
            if (line.startsWith("Reservation ID: ")) {
                form.setId(line.split(": ")[1]);
            } else if (line.startsWith("Passenger Name: ")) {
                form.setName(line.split(": ")[1]);
            } else if (line.startsWith("Birth Date: ")) {
                form.setBirthDate(line.split(": ")[1]);
            } else if (line.startsWith("Gender: ")) {
                form.setGender(line.split(": ")[1]);
            } else if (line.startsWith("Contact: ")) {
                String[] parts = line.split(": ")[1].split(", ");
                form.setMobileNumber(parts[0].trim());
                form.setEmail(parts.length > 1 ? parts[1].trim() : "");
            } else if (line.startsWith("Language: ")) {
                form.setLanguage(line.split(": ")[1]);
            } else if (line.startsWith("Mileage: ")) {
                form.setCarrierForMileageAccumulation(line.split(": ")[1]);
            } else if (line.startsWith("Membership Number: ")) {
                form.setMembershipNumber(line.split(": ")[1]);
            } else if (line.startsWith("Flight: ")) {
                String[] parts = line.split(": ")[1].split(", ");
                String[] route = parts[2].split(" → ");
                Flight flight = new Flight(parts[0], route[0].trim(), route[1].trim(), parts[1], new ArrayList<>());
                ReservedFlight rf = new ReservedFlight();
                rf.setFlight(flight);
                flights.add(rf);
            } else if (line.startsWith("Class: ")) {
                ReservedFlight last = flights.get(flights.size() - 1);
                String[] seatParts = line.split(", ");
                last.setCabinClass(seatParts[0].split(": ")[1]);
                last.setSeatCount(Integer.parseInt(seatParts[1].split(": ")[1]));
            }
        }

        form.setReservedFlights(new ArrayList<>(flights));
        return form;
    }
}
