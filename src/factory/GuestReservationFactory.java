package factory;

import dto.Flight;
import dto.ReservationForm;
import dto.ReservedFlight;

import java.util.*;

public class GuestReservationFactory extends ReservationFactory {

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
            if (line.startsWith("Reservation ID: ")) {
                form.setId(line.split(": ")[1]);
            } else if (line.startsWith("Passenger Name: ")) {
                form.setName(line.split(": ")[1]);
            } else if (line.startsWith("Birth Date: ")) {
                form.setBirthDate(line.split(": ")[1]);
            } else if (line.startsWith("Gender: ")) {
                form.setGender(line.split(": ")[1]);
            } else if (line.startsWith("Contact: +")) {
                String[] parts = line.split(": ")[1].split(", ");
                form.setMobileNumber(parts[0].split(" ", 2)[1]);
                form.setEmail(parts[1]);
            } else if (line.startsWith("Language: ")) {
                form.setLanguage(line.split(": ")[1]);
            } else if (line.startsWith("Guest Password: ")) {
                form.setRegisterGuestPassword(line.split(": ")[1]);
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
