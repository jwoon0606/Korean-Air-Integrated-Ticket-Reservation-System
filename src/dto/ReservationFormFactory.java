package dto;


import java.util.*;

public class ReservationFormFactory {
	
    // 1. LineHandler 인터페이스 정의
    @FunctionalInterface
    interface LineHandler {
        void handle(String line, ReservationForm form, List<ReservedFlight> flights);
    }

    // 2. 핸들러 등록을 위한 Map
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

        handlers.put("Language: ", (line, form, flights) ->
                form.setLanguage(line.split(": ")[1]));

        handlers.put("Guest Password: ", (line, form, flights) ->
                form.setRegisterGuestPassword(line.split(": ")[1]));

        handlers.put("Contact: +", (line, form, flights) -> {
            String[] parts = line.split(": ")[1].split(", ");
            String[] countryMobile = parts[0].split(" ");
            form.setCountryCode(countryMobile[0]);
            form.setMobileNumber(countryMobile[1]);
            form.setEmail(parts[1]);
        });

        handlers.put("Flight: ", (line, form, flights) -> {
            try {
                String[] parts = line.split(": ")[1].split(", ");
                if (parts.length == 3) {
                    String flightNumber = parts[0].trim();
                    String departureDate = parts[1].trim();
                    String[] route = parts[2].split(" → ");
                    String departure = route[0].trim();
                    String destination = route[1].trim();

                    Flight f = new Flight(flightNumber, departure, destination, departureDate, new ArrayList<>());
                    ReservedFlight rf = new ReservedFlight();
                    rf.setFlight(f);
                    flights.add(rf);
                } else {
                    System.out.println("[경고] 'Flight' 라인의 형식이 잘못되었습니다: " + line);
                }
            } catch (Exception e) {
                System.out.println("[오류] 'Flight' 라인 파싱 실패: " + line);
            }
        });

        handlers.put("Class: ", (line, form, flights) -> {
            ReservedFlight last = flights.get(flights.size() - 1);
            String[] seatData = line.split(", ");
            last.setCabinClass(seatData[0].split(": ")[1]);
            last.setSeatCount(Integer.parseInt(seatData[1].split(": ")[1]));
        });
    }

    // 3. 실제 Factory 메서드
    public static ReservationForm fromTextBlock(List<String> lines) {
        ReservationForm form = new ReservationForm();
        List<ReservedFlight> flights = new ArrayList<>();

        for (String line : lines) {
            boolean matched = false;
            for (Map.Entry<String, LineHandler> entry : handlers.entrySet()) {
                if (line.startsWith(entry.getKey())) {
                    entry.getValue().handle(line, form, flights);
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                System.out.println("[경고] 처리되지 않은 라인: " + line);
            }
        }

        form.setReservedFlights(new ArrayList<>(flights));
        return form;
    }
}
