package controller;

import dto.*;
import user.User;
import user.GuestPassenger;
import user.RegisteredPassenger;
import strategy.FlightLoadStrategy;
import strategy.LoadStrategy;
import strategy.SaveStrategy;
import factory.*;
import service.ReservationService;
import service.BasicReservationService;
import service.decorator.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ReservationController {
    private LoadStrategy<ReservationForm> loadStrategy;
    private SaveStrategy<ReservationForm> saveStrategy;
    private static ReservationController reservationController = null;
    private static final String RESERVATION_FILE = "src/file/ReservationList.txt"; 
    private final Scanner scanner;
    private final FlightController flightController;
    private final LoginController loginController; // 현 user 판별용
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

    public void setLoadStrategy(LoadStrategy<ReservationForm> loadStrategy) {
        this.loadStrategy = loadStrategy;
    }

    public void setSaveStrategy(SaveStrategy<ReservationForm> saveStrategy) {
        this.saveStrategy = saveStrategy;
    }

    public synchronized static ReservationController getReservationController(LoginController loginController) {
        if(reservationController == null) {
            reservationController = new ReservationController(loginController);
        }
        return reservationController;
    }

    private ReservationController(LoginController loginController) {
    	this.loginController = loginController;
        scanner = new Scanner(System.in);
        flightController = new FlightController();
        flightController.setLoadStrategy(new FlightLoadStrategy());
    }

    // 1. 텍스트 파일에서 ReservationForm 목록 생성
    public List<ReservationForm> loadAllReservations() {
        return loadStrategy.load();
    }

    // 2. Registered 사용자: 이메일 기반 필터
    public List<ReservationForm> findReservationsByEmail(String email) {
        List<ReservationForm> matched = new ArrayList<>();
        for (ReservationForm r : loadAllReservations()) {
            if (r.getEmail() != null && r.getEmail().equalsIgnoreCase(email)) {
                matched.add(r);
            }
        }

        if (matched.isEmpty()) {
            System.out.println("[notice] No Reservation by this email");
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
    
    private ReservationFactory getFactoryForCurrentUser() {
        User user = loginController.getCurrentUser();
        if (user instanceof RegisteredPassenger rp) {
            return new RegisteredReservationFactory(rp);
        } else if (user instanceof GuestPassenger) {
            return new GuestReservationFactory();
        } else {
            throw new IllegalStateException("[error] unchecked user type ");
        }
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

        // 팩토리를 통해 예약 정보 생성
        ReservationFactory factory = getFactoryForCurrentUser();  // 현재 로그인된 사용자 기준
        ReservationForm form = factory.create(scanner, flights);

        // 추가 서비스 선택 (Decorator Pattern 적용)
        System.out.println("\n=== 추가 서비스 선택 ===");
        System.out.println("부가서비스를 추가하시겠습니까? (yes/no)");
        String addServices = scanner.nextLine().trim();
        
        if (addServices.equalsIgnoreCase("yes")) {
            ReservationService service = new BasicReservationService(flights);
            
            // 기내식 서비스 추가
            service = addMealServiceIfRequested(service);
            
            // 수하물 서비스 추가
            service = addBaggageServiceIfRequested(service);
            
            // 라운지 서비스 추가
            service = addLoungeServiceIfRequested(service);
            
            // 추가 서비스 정보를 ReservationForm에 저장
            form.setAdditionalServices(service.getFeatures());
            
            // 기본 항공편 가격을 제외한 추가 서비스 비용만 계산
            double basePrice = new BasicReservationService(flights).calculatePrice();
            double additionalPrice = service.calculatePrice() - basePrice;
            form.setTotalAdditionalServicePrice(additionalPrice);
            
            System.out.println("\n=== 서비스 요약 ===");
            System.out.println(service.getDescription());
            System.out.println("추가 서비스 비용: $" + String.format("%.2f", additionalPrice));
        }

        // 예약 정보 저장
        saveReservationToFile(form);

        // 예약 ID 출력 (특히 Guest에게 필요)
        System.out.println("\nReservation completed and saved successfully!");
        System.out.println("Your reservation ID is: " + form.getId());
        System.out.println("Please keep this ID to check or manage your reservation later.");
    }


    /**
     * Deletes the most recent reservation for a specific user.
     * @param userEmail The email of the user whose reservation is to be cancelled.
     * @return The ReservationForm that was cancelled, or null if no reservation was found for the user or an error occurred.
     */
    public ReservationForm deleteReservation(String userEmail) {
        List<ReservationForm> allReservations = loadAllReservations();
        List<ReservationForm> userReservations = new ArrayList<>();
        ReservationForm reservationToCancel = null;

        int lastUserReservationIndexInAll = -1;
        for (int i = 0; i < allReservations.size(); i++) {
            ReservationForm r = allReservations.get(i);
            if (r.getEmail().equalsIgnoreCase(userEmail)) {
                userReservations.add(r);
                lastUserReservationIndexInAll = i;
            }
        }

        if (userReservations.isEmpty() || lastUserReservationIndexInAll == -1) {
            return null;
        }

        // The reservation to cancel is the one at lastUserReservationIndexInAll
        reservationToCancel = allReservations.remove(lastUserReservationIndexInAll);

        if (overwriteAllReservations(allReservations)) {
            return reservationToCancel;
        } else {
            return null; 
        }
    }
    
    /**
     * Adds a specific reservation back to the list and saves it.
     * Used for undoing a deletion.
     * @param form The ReservationForm to add.
     * @return true if the reservation was added and saved successfully, false otherwise.
     */
    public boolean restoreReservation(ReservationForm form) {
        if (form == null) {
            return false;
        }
        List<ReservationForm> reservations = loadAllReservations();
        reservations.add(form); // Add to the end, or sort if order matters and is maintained
        return overwriteAllReservations(reservations);
    }

    /**
     * 제공된 모든 예약 정보를 파일에 덮어씁니다.
     * @param reservations 파일에 저장할 ReservationForm 객체의 리스트
     * @return 파일 쓰기에 성공하면 true, 그렇지 않으면 false를 반환합니다.
     */
    private boolean overwriteAllReservations(List<ReservationForm> reservations) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESERVATION_FILE, false))) { // false는 덮어쓰기 모드를 의미
            for (ReservationForm form : reservations) {
                List<String> textBlock = form.toTextBlock(); // ReservationForm에 이 메소드가 구현되어 있어야 함
                for (String line : textBlock) {
                    writer.write(line);
                    writer.newLine();
                }
                writer.write("----"); // 각 예약 정보 블록 다음에 구분자 추가
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("[오류] 예약 파일 전체 쓰기 실패: " + e.getMessage());
            return false;
        }
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


    // Decorator Pattern service addition methods
    private ReservationService addMealServiceIfRequested(ReservationService service) {
        System.out.println("기내식 서비스를 추가하시겠습니까? (yes/no)");
        String choice = scanner.nextLine().trim();
        
        if (choice.equalsIgnoreCase("yes")) {
            System.out.println("기내식 종류를 선택하세요:");
            System.out.println("1. 일반식 (+$25)");
            System.out.println("2. 채식 (+$25)");
            System.out.println("3. 할랄식 (+$30)");
            System.out.print("선택 (1-3): ");
            
            int mealChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            String mealType;
            double mealPrice;
            
            switch (mealChoice) {
                case 1:
                    mealType = "일반식";
                    mealPrice = 25.0;
                    break;
                case 2:
                    mealType = "채식";
                    mealPrice = 25.0;
                    break;
                case 3:
                    mealType = "할랄식";
                    mealPrice = 30.0;
                    break;
                default:
                    mealType = "일반식";
                    mealPrice = 25.0;
                    break;
            }
            
            return new MealDecorator(service, mealType, mealPrice);
        }
        
        return service;
    }
    
    private ReservationService addBaggageServiceIfRequested(ReservationService service) {
        System.out.println("추가 수하물 서비스를 이용하시겠습니까? (yes/no)");
        String choice = scanner.nextLine().trim();
        
        if (choice.equalsIgnoreCase("yes")) {
            System.out.println("추가 수하물을 몇 개 추가하시겠습니까?");
            System.out.println("1. 1개 (+$50)");
            System.out.println("2. 2개 (+$90)");
            System.out.println("3. 3개 (+$120)");
            System.out.print("선택 (1-3): ");
            
            int baggageChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            int bags;
            double bagPrice;
            
            switch (baggageChoice) {
                case 1:
                    bags = 1;
                    bagPrice = 50.0;
                    break;
                case 2:
                    bags = 2;
                    bagPrice = 45.0; // $90 / 2 bags
                    break;
                case 3:
                    bags = 3;
                    bagPrice = 40.0; // $120 / 3 bags
                    break;
                default:
                    bags = 1;
                    bagPrice = 50.0;
                    break;
            }
            
            return new BaggageDecorator(service, bags, bagPrice);
        }
        
        return service;
    }
    
    private ReservationService addLoungeServiceIfRequested(ReservationService service) {
        System.out.println("라운지 접근 서비스를 이용하시겠습니까? (yes/no)");
        String choice = scanner.nextLine().trim();
        
        if (choice.equalsIgnoreCase("yes")) {
            System.out.println("라운지 타입을 선택하세요:");
            System.out.println("1. 비즈니스 라운지 (+$30)");
            System.out.println("2. 프리미엄 라운지 (+$60)");
            System.out.print("선택 (1-2): ");
            
            int loungeChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            String loungeType;
            double loungePrice;
            
            switch (loungeChoice) {
                case 1:
                    loungeType = "비즈니스 라운지";
                    loungePrice = 30.0;
                    break;
                case 2:
                    loungeType = "프리미엄 라운지";
                    loungePrice = 60.0;
                    break;
                default:
                    loungeType = "비즈니스 라운지";
                    loungePrice = 30.0;
                    break;
            }
            
            return new LoungAccessDecorator(service, loungeType, loungePrice);
        }
        
        return service;
    }

    private void saveReservationToFile(ReservationForm form) {
        saveStrategy.save(form);
    }
}