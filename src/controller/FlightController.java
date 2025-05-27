package controller;

import config.Constants;
import dto.Flight;
import dto.Seat;
import strategy.LoadStrategy;

import java.io.*;
import java.util.*;

public class FlightController {
    private List<Flight> flights;
    private LoadStrategy loadStrategy;

    public FlightController() {

    }

    public List<Flight> getAvailableFlights() {
        return flights;
    }

    public void setLoadStrategy(LoadStrategy loadStrategy) {
        this.loadStrategy = loadStrategy;
        loadFlightsFromFile();
    }

    public void loadFlightsFromFile() {
        flights = loadStrategy.load();
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
