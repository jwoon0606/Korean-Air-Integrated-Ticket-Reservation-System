package controller;

import dto.Flight;
import dto.Seat;

import java.io.*;
import java.util.*;

public class FlightController {
    private List<Flight> flights = new ArrayList<>();

    public FlightController() {
        loadFlightsFromFile("FlightList.txt", "SeatList.txt");
    }

    public List<Flight> getAvailableFlights() {
        return flights;
    }

    public void loadFlightsFromFile(String flightFile, String seatFile) {
        try {
            BufferedReader flightReader = new BufferedReader(new FileReader(flightFile));
            String line;
            while ((line = flightReader.readLine()) != null) {
                String[] flightData = line.split(",");
                String flightNumber = flightData[0];
                String departure = flightData[1];
                String destination = flightData[2];
                String departureDate = flightData[3];
                List<Seat> seats = loadSeatsForFlight(flightNumber, seatFile);
                Flight flight = new Flight(flightNumber, departure, destination, departureDate, seats);
                flights.add(flight);
            }
            flightReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Seat> loadSeatsForFlight(String flightNumber, String seatFile) {
        List<Seat> seats = new ArrayList<>();
        try {
            BufferedReader seatReader = new BufferedReader(new FileReader(seatFile));
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

    public Flight findFlight(String departure, String destination) {
        for (Flight flight : flights) {
            if (flight.getDeparture().equalsIgnoreCase(departure) && flight.getDestination().equalsIgnoreCase(destination)) {
                return flight;
            }
        }
        return null;
    }
}
