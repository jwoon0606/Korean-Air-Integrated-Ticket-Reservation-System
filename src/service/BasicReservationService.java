package service;

import dto.ReservationForm;
import dto.ReservedFlight;
import java.util.Arrays;
import java.util.List;

/**
 * 기본 예약 서비스 ConcreteComponent
 * 데코레이터 패턴의 기본 구현체로 도로의 BasicRoad 역할
 */
public class BasicReservationService implements ReservationService {
    private List<ReservedFlight> flights;
    private double basePrice;
    
    public BasicReservationService(List<ReservedFlight> flights) {
        this.flights = flights;
        this.basePrice = calculateBasePrice(flights);
    }
    
    public BasicReservationService(List<ReservedFlight> flights, double customBasePrice) {
        this.flights = flights;
        this.basePrice = customBasePrice;
    }
    
    @Override
    public String getDescription() {
        StringBuilder desc = new StringBuilder("기본 항공편 예약");
        if (flights != null && !flights.isEmpty()) {
            desc.append(" (").append(flights.size()).append("개 항공편)");
        }
        return desc.toString();
    }
    
    @Override
    public double calculatePrice() {
        return basePrice;
    }
    
    @Override
    public List<String> getFeatures() {
        return Arrays.asList("항공편 좌석 예약");
    }
    
    @Override
    public ReservationForm processService(ReservationForm form) {
        // 기본 서비스는 추가 처리 없이 그대로 반환
        System.out.println("[Basic Service] 기본 예약 서비스 처리 완료");
        return form;
    }
    
    /**
     * 항공편 목록을 기반으로 기본 가격 계산
     */
    private double calculateBasePrice(List<ReservedFlight> flights) {
        if (flights == null || flights.isEmpty()) {
            return 0.0;
        }
        
        double total = 0.0;
        for (ReservedFlight flight : flights) {
            // 간단한 가격 계산 로직 (실제로는 더 복잡할 수 있음)
            String cabinClass = flight.getCabinClass();
            int seatCount = flight.getSeatCount();
            
            double baseFlightPrice = switch (cabinClass.toLowerCase()) {
                case "economy" -> 500.0;
                case "business" -> 1200.0;
                case "first" -> 2500.0;
                default -> 500.0;
            };
            
            total += baseFlightPrice * seatCount;
        }
        
        return total;
    }
    
    // Getter methods
    public List<ReservedFlight> getFlights() {
        return flights;
    }
    
    public double getBasePrice() {
        return basePrice;
    }
}
