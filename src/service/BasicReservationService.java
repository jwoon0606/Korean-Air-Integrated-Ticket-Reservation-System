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
        StringBuilder desc = new StringBuilder("Basic flight reservation");
        if (flights != null && !flights.isEmpty()) {
            desc.append(" (").append(flights.size()).append(" flights)");
        }
        return desc.toString();
    }
    
    @Override
    public double calculatePrice() {
        return basePrice;
    }
    
    @Override
    public List<String> getFeatures() {
        return Arrays.asList("Flight seat reservation");
    }
    
    @Override
    public ReservationForm processService(ReservationForm form) {
        // Basic service returns form without additional processing
        System.out.println("[Basic Service] Basic reservation service processing completed");
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

}
