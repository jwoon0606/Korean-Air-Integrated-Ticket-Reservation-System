package strategy;

import config.Constants;
import dto.Flight;
import dto.Seat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlightLoadStrategy implements LoadStrategy<Flight>{
    @Override
    public List<Flight> load() {
        List<Flight> flights = new ArrayList<>();

        try {
            BufferedReader flightReader = new BufferedReader(new FileReader(Constants.FLIGHT_FILE));
            String line;
            while ((line = flightReader.readLine()) != null) {
                String[] flightData = line.split(",");
                String flightNumber = flightData[0];
                String departure = flightData[1];
                String destination = flightData[2];
                String departureDate = flightData[3];
                List<Seat> seats = loadSeatsForFlight(flightNumber);
                Flight flight = new Flight(flightNumber, departure, destination, departureDate, seats);
                flights.add(flight);
            }
            flightReader.close();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return flights;
    }

    private List<Seat> loadSeatsForFlight(String flightNumber) {
        List<Seat> seats = new ArrayList<>();
        try {
            BufferedReader seatReader = new BufferedReader(new FileReader(Constants.SEAT_FILE));
            String line;
            while ((line = seatReader.readLine()) != null) {
                String[] seatData = line.split(",");
                String seatFlightNumber = seatData[0];
                if (seatFlightNumber.equals(flightNumber)) {
                    String cabinClass = seatData[1];
                    int availableSeats = Integer.parseInt(seatData[2]);
                    double price = Double.parseDouble(seatData[3]);
                    seats.add(new Seat(cabinClass, availableSeats, price));
                }
            }
            seatReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return seats;
    }


}
