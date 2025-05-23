package controller;

import dto.Flight;
import dto.ReservationFormFactory;
import dto.ReservationForm;
import dto.ReservedFlight;
import dto.Seat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.util.*;

public class ReservationController {
    private final Scanner scanner;
    private final FlightController flightController;
    private static final String RESERVATION_FILE = "src/file/ReservationList.txt"; // 예약 정보 파일 경로
    private static final List<List<String>> COUNTRY_DATA = List.of(
            List.of("Republic of Korea"),
            List.of("China", "Japan", "Mongolia"),
            List.of("Thailand", "Vietnam", "Indonesia", "Malaysia"),
            List.of("India", "Pakistan", "Bangladesh"),
            List.of("United Kingdom", "Germany", "France", "Italy", "Spain"),
            List.of("United States", "Canada"),
            List.of("Mexico", "Costa Rica"),
            List.of("Brazil", "Argentina", "Chile"),
            List.of("Australia", "New Zealand"),
            List.of("South Africa", "Egypt", "Nigeria")
    );

    public ReservationController() {
        scanner = new Scanner(System.in);
        flightController = new FlightController();
    }
    
 // 1. 텍스트 파일에서 ReservationForm 목록 생성
    public List<ReservationForm> loadAllReservations() {
        List<ReservationForm> reservations = new ArrayList<>();
        List<String> currentBlock = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(RESERVATION_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("----")) {
                    reservations.add(ReservationFormFactory.fromTextBlock(currentBlock));
                    currentBlock.clear();
                } else {
                    currentBlock.add(line);
                }
            }
            if (!currentBlock.isEmpty()) {
                reservations.add(ReservationFormFactory.fromTextBlock(currentBlock));
            }
        } catch (IOException e) {
            System.out.println("[오류] 예약 파일 읽기 실패: " + e.getMessage());
        }
        return reservations;
    }

    // 2. Registered 사용자: 이메일 기반 필터
    public List<ReservationForm> findReservationsByEmail(String email) {
        List<ReservationForm> matched = new ArrayList<>();
        for (ReservationForm r : loadAllReservations()) {
            if (r.getEmail().equalsIgnoreCase(email)) {
                matched.add(r);
            }
        }
        return matched;
    }

    // 3. Guest 사용자: ID + 비밀번호 기반 필터
    public ReservationForm findByReservationIdAndPassword(String id, String password) {
        for (ReservationForm r : loadAllReservations()) {
            if (r.getId().equals(id) && r.getRegisterGuestPassword().equals(password)) {
                return r;
            }
        }
        return null;
    }
    

    public void bookFlight() {
        ArrayList<ReservedFlight> flights = selectFlight();

        System.out.println("\nWould you like to proceed with reservation? (yes/no)");
        scanner.nextLine(); // consume newline
        String proceed = scanner.nextLine().trim();

        if (!proceed.equalsIgnoreCase("yes")) {
            System.out.println("Reservation cancelled.");
            return;
        }

        ReservationForm form = getReservationDetails(flights);
        saveReservationToFile(form);

        System.out.println("\nReservation completed and saved successfully!");
    }

    public ArrayList<ReservedFlight> selectFlight() {
        System.out.println("\n* Select Flight *");
        System.out.println("** Select Country **");

        System.out.println("-Departure-");
        int departureContinent = selectContinent();
        int departureCountryIndex = showCountriesForContinent(departureContinent);
        String departureCountry = COUNTRY_DATA.get(departureContinent - 1).get(departureCountryIndex - 1);
        System.out.println("You selected departure: " + departureCountry);

        System.out.println("\n-Arrival-");
        int arrivalContinent = selectContinent();
        int arrivalCountryIndex = showCountriesForContinent(arrivalContinent);
        String arrivalCountry = COUNTRY_DATA.get(arrivalContinent - 1).get(arrivalCountryIndex - 1);
        System.out.println("You selected arrival: " + arrivalCountry);

        String result = "From " + departureCountry + " to " + arrivalCountry;
        System.out.println(result);


        String[] departureDates = inputTravelDates();
        String cabinClass = selectCabinClass();

        Flight departureFlight = flightController.findFlight(departureCountry, arrivalCountry);
        if (departureFlight != null && departureFlight.getDepartureDate().equals(departureDates[0])) {
            Optional<Seat> seat = departureFlight.getSeats().stream()
                    .filter(s -> s.getCabinClass().equalsIgnoreCase(cabinClass))
                    .findFirst();
            if (seat.isPresent()) {
                System.out.println("\n[Departure Flight]");
                System.out.println("Date: " + departureDates[0]);
                System.out.println("Available seats for " + cabinClass + ": " + seat.get().getAvailableSeats());
            } else {
                System.out.println("No seat information found for class: " + cabinClass + " (departure)");
            }
        } else {
            System.out.println("No matching departure flight found.");
        }

        Flight returnFlight = flightController.findFlight(arrivalCountry, departureCountry);
        if (returnFlight != null && returnFlight.getDepartureDate().equals(departureDates[1])) {
            Optional<Seat> seat = returnFlight.getSeats().stream()
                    .filter(s -> s.getCabinClass().equalsIgnoreCase(cabinClass))
                    .findFirst();
            if (seat.isPresent()) {
                System.out.println("\n[Return Flight]");
                System.out.println("Date: " + departureDates[1]);
                System.out.println("Available seats for " + cabinClass + ": " + seat.get().getAvailableSeats());
            } else {
                System.out.println("No seat information found for class: " + cabinClass + " (return)");
            }
        } else {
            System.out.println("No matching return flight found.");
        }

        ArrayList<ReservedFlight> flights = new ArrayList<>();

        if (departureFlight != null && returnFlight != null &&
                departureFlight.getDepartureDate().equals(departureDates[0]) &&
                returnFlight.getDepartureDate().equals(departureDates[1])) {

            Optional<Seat> departureSeatOpt = departureFlight.getSeats().stream()
                    .filter(s -> s.getCabinClass().equalsIgnoreCase(cabinClass))
                    .findFirst();
            Optional<Seat> returnSeatOpt = returnFlight.getSeats().stream()
                    .filter(s -> s.getCabinClass().equalsIgnoreCase(cabinClass))
                    .findFirst();

            if (departureSeatOpt.isPresent() && returnSeatOpt.isPresent()) {
                Seat departureSeat = departureSeatOpt.get();
                Seat returnSeat = returnSeatOpt.get();

                System.out.print("\nHow many people would you like to reserve seats for? ");
                int numberOfPeople = scanner.nextInt();

                if (numberOfPeople <= departureSeat.getAvailableSeats()
                        && numberOfPeople <= returnSeat.getAvailableSeats()) {
                    System.out.println("Reservation possible for " + numberOfPeople + " people.");

                    ReservedFlight departureReservedFlight = new ReservedFlight();
                    departureReservedFlight.setFlight(departureFlight);
                    departureReservedFlight.setCabinClass(cabinClass);
                    departureReservedFlight.setSeatCount(numberOfPeople);

                    ReservedFlight returnReservedFlight = new ReservedFlight();
                    returnReservedFlight.setFlight(returnFlight);
                    returnReservedFlight.setCabinClass(cabinClass);
                    returnReservedFlight.setSeatCount(numberOfPeople);

                    flights.add(departureReservedFlight);
                    flights.add(returnReservedFlight);
                } else {
                    System.out.println("Not enough seats available in " + cabinClass + " class.");
                    System.out.println("Departure available: " + departureSeat.getAvailableSeats());
                    System.out.println("Return available: " + returnSeat.getAvailableSeats());
                }
            }
        }

        return flights;
    }

    private int selectContinent() {
        int continentMaxNumber = 10;
        System.out.println("1. Republic of Korea\n" +
                "2. Northeast Asia\n" +
                "3. Southeast Asia\n" +
                "4. South Asia\n" +
                "5. Europe\n" +
                "6. North America\n" +
                "7. Central America\n" +
                "8. South America\n" +
                "9. Oceania\n" +
                "10. Africa\n");
        System.out.print("Select a continent (1-10): ");
        int continentChoice = scanner.nextInt();

        while (continentChoice < 1 || continentChoice > continentMaxNumber) {
            System.out.print("Invalid choice. Please enter a number between 1 and 10: ");
            continentChoice = scanner.nextInt();
        }

        return continentChoice;
    }

    public int showCountriesForContinent(int continentChoice) {
        int choice = 0;
        switch (continentChoice) {
            case 1:
                System.out.println("You selected Republic of Korea.");
                System.out.println("1. Korea");
                choice = selectCountry(1);
                break;
            case 2:
                System.out.println("You selected Northeast Asia. Please choose a country:");
                System.out.println("1. China");
                System.out.println("2. Japan");
                System.out.println("3. Mongolia");
                choice = selectCountry(3);
                break;
            case 3:
                System.out.println("You selected Southeast Asia. Please choose a country:");
                System.out.println("1. Thailand");
                System.out.println("2. Vietnam");
                System.out.println("3. Indonesia");
                System.out.println("4. Malaysia");
                choice = selectCountry(4);
                break;
            case 4:
                System.out.println("You selected South Asia. Please choose a country:");
                System.out.println("1. India");
                System.out.println("2. Pakistan");
                System.out.println("3. Bangladesh");
                choice = selectCountry(3);
                break;
            case 5:
                System.out.println("You selected Europe. Please choose a country:");
                System.out.println("1. United Kingdom");
                System.out.println("2. Germany");
                System.out.println("3. France");
                System.out.println("4. Italy");
                System.out.println("5. Spain");
                choice = selectCountry(5);
                break;
            case 6:
                System.out.println("You selected North America. Please choose a country:");
                System.out.println("1. United States");
                System.out.println("2. Canada");
                choice = selectCountry(2);
                break;
            case 7:
                System.out.println("You selected Central America. Please choose a country:");
                System.out.println("1. Mexico");
                System.out.println("2. Costa Rica");
                choice = selectCountry(2);
                break;
            case 8:
                System.out.println("You selected South America. Please choose a country:");
                System.out.println("1. Brazil");
                System.out.println("2. Argentina");
                System.out.println("3. Chile");
                choice = selectCountry(3);
                break;
            case 9:
                System.out.println("You selected Oceania. Please choose a country:");
                System.out.println("1. Australia");
                System.out.println("2. New Zealand");
                choice = selectCountry(2);
                break;
            case 10:
                System.out.println("You selected Africa. Please choose a country:");
                System.out.println("1. South Africa");
                System.out.println("2. Egypt");
                System.out.println("3. Nigeria");
                choice = selectCountry(3);
                break;
        }

        return choice;
    }

    public int selectCountry(int maxChoice) {
        System.out.print("Enter the number of your choice (1-" + maxChoice + "): ");
        int countryChoice = scanner.nextInt();

        while (countryChoice < 1 || countryChoice > maxChoice) {
            System.out.print("Invalid choice. Please enter a number between 1 and " + maxChoice + ": ");
            countryChoice = scanner.nextInt();
        }

        return countryChoice;
    }

    public String[] inputTravelDates() {
        System.out.println("\n** Select Dates **");
        scanner.nextLine();

        System.out.print("Enter departure date (YYYY-MM-DD): ");
        String departureDate = scanner.nextLine();

        System.out.print("Enter return date (YYYY-MM-DD): ");
        String returnDate = scanner.nextLine();

        return new String[] { departureDate, returnDate };
    }

    public String selectCabinClass() {
        System.out.println("\n** Select Cabin Class **");
        System.out.println("1. Economy");
        System.out.println("2. Business");
        System.out.println("3. First");
        System.out.print("Enter your choice (1-3): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        while (choice < 1 || choice > 3) {
            System.out.print("Invalid choice. Please enter a number between 1 and 3: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        }

        return switch (choice) {
            case 1 -> "Economy";
            case 2 -> "Business";
            case 3 -> "First";
            default -> "Economy";
        };
    }

    public ReservationForm getReservationDetails(ArrayList<ReservedFlight> reservedFlights) {
        System.out.println("\n** Passenger Details **");

        String id = UUID.randomUUID().toString();  // 시스템이 자동으로 ID 생성

        System.out.print("Enter your full name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your gender (M/F): ");
        String gender = scanner.nextLine();

        System.out.print("Enter your birth date (YYYY-MM-DD): ");
        String birthDate = scanner.nextLine();

        System.out.print("Enter your mileage airline: ");
        String carrierForMileageAccumulation = scanner.nextLine();

        System.out.print("Enter your membership number: ");
        String membershipNumber = scanner.nextLine();

        System.out.print("Enter your country code (e.g., 82): ");
        String countryCode = scanner.nextLine();

        System.out.print("Enter your mobile number: ");
        String mobileNumber = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Preferred language: ");
        String language = scanner.nextLine();

        System.out.print("Enter guest password: ");
        String registerGuestPassword = scanner.nextLine();

        System.out.println("\nYour reservation ID is: " + id);
        System.out.println("Please keep this ID to check or manage your reservation later.");

        return new ReservationForm(
                id, name, gender, birthDate,
                carrierForMileageAccumulation, membershipNumber,
                countryCode, mobileNumber, email, language,
                registerGuestPassword, reservedFlights
        );
    }


    private void saveReservationToFile(ReservationForm form) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESERVATION_FILE, true))) {
            writer.write("Reservation ID: " + form.getId() + "\n");
            writer.write("Passenger Name: " + form.getName() + "\n");
            writer.write("Birth Date: " + form.getBirthDate() + "\n");
            writer.write("Gender: " + form.getGender() + "\n");
            writer.write("Contact: +" + form.getCountryCode() + " " + form.getMobileNumber() + ", " + form.getEmail() + "\n");
            writer.write("Language: " + form.getLanguage() + "\n");
            writer.write("Guest Password: " + form.getRegisterGuestPassword() + "\n");

            for (ReservedFlight rf : form.getReservedFlights()) {
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