package controller;

import java.util.List;
import java.util.Scanner;

public class ReservationController {
    private final Scanner scanner;
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
    }

    public String selectFlight() {
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

        return result;
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

    /*
    public ReservationForm getReservationDetails() {
        System.out.println("Input Reservation Data.");

        System.out.print("ID: ");
        String id = scanner.nextLine();

        System.out.print("이름: ");
        String name = scanner.nextLine();

        System.out.print("성별 (예: M/F): ");
        String gender = scanner.nextLine();

        System.out.print("생년월일 (YYYY-MM-DD): ");
        String birthDate = scanner.nextLine();

        System.out.print("적립 항공사: ");
        String carrierForMileageAccumulation = scanner.nextLine();

        System.out.print("회원 번호: ");
        String membershipNumber = scanner.nextLine();

        System.out.print("국가 코드: ");
        String countryCode = scanner.nextLine();

        System.out.print("휴대폰 번호: ");
        String mobileNumber = scanner.nextLine();

        System.out.print("이메일: ");
        String email = scanner.nextLine();

        System.out.print("언어: ");
        String language = scanner.nextLine();

        System.out.print("예약 비밀번호 (비회원인 경우만 입력): ");
        String registerGuestPassword = scanner.nextLine();

        ArrayList<Flight> flights = new ArrayList<>();
        System.out.print("예약할 항공편 수: ");
        int flightCount = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < flightCount; i++) {
            System.out.println("예약할 항공편 " + (i + 1) + " 정보를 입력하세요.");
            System.out.print("항공편 번호: ");
            String flightNumber = scanner.nextLine();

            System.out.print("출발지: ");
            String departure = scanner.nextLine();

            System.out.print("도착지: ");
            String destination = scanner.nextLine();

            System.out.print("출발일 (YYYY-MM-DD): ");
            String departureDate = scanner.nextLine();

            Flight flight = new Flight(flightNumber, departure, destination, departureDate, "");
            flights.add(flight);
        }

        return new ReservationForm(id, name, gender, birthDate, carrierForMileageAccumulation, membershipNumber, countryCode, mobileNumber, email, language, registerGuestPassword, flights);
    }
     */
}