package strategy;

import config.Constants;
import dto.Flight;
import dto.ReservationForm;
import dto.ReservedFlight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ReservationSaveStrategy implements SaveStrategy<ReservationForm>{
    @Override
    public void save(ReservationForm data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.RESERVATION_FILE, true))) {
            writer.write("Reservation ID: " + data.getId() + "\n");
            writer.write("Passenger Name: " + data.getName() + "\n");
            writer.write("Birth Date: " + data.getBirthDate() + "\n");
            writer.write("Gender: " + data.getGender() + "\n");
            writer.write("Contact: +" + data.getCountryCode() + " " + data.getMobileNumber() + ", " + data.getEmail() + "\n");
            writer.write("Language: " + data.getLanguage() + "\n");
            writer.write("Guest Password: " + data.getRegisterGuestPassword() + "\n");

            for (ReservedFlight rf : data.getReservedFlights()) {
                Flight flight = rf.getFlight();
                writer.write("Flight: " + flight.getFlightNumber() + ", " + flight.getDepartureDate() + ", "
                        + flight.getDeparture() + " → " + flight.getDestination() + "\n");
                writer.write("Class: " + rf.getCabinClass() + ", Seats Reserved: " + rf.getSeatCount() + "\n");
            }

            writer.write("----\n");
        } catch (IOException e) {
            System.out.println("Failed to save reservation: " + e.getMessage());
        }
    }
}
