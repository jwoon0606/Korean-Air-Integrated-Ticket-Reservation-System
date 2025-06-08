package service.decorator;

import service.ReservationService;
import dto.ReservationForm;
import java.util.ArrayList;
import java.util.List;

/**
 * 예약 서비스 데코레이터 추상클래스
 * 데코레이터 패턴의 Decorator 역할로 도로의 RoadDecorator 역할
 */
public abstract class ReservationServiceDecorator implements ReservationService {
    protected ReservationService reservationService;
    
    public ReservationServiceDecorator(ReservationService reservationService) {
        if (reservationService == null) {
            throw new IllegalArgumentException("ReservationService cannot be null");
        }
        this.reservationService = reservationService;
    }
    
    /**
     * 기본 구현은 래핑된 서비스에 위임 (도로의 기본 기능 유지)
     */
    @Override
    public String getDescription() {
        return reservationService.getDescription();
    }
    
    @Override
    public double calculatePrice() {
        return reservationService.calculatePrice();
    }
    
    @Override
    public List<String> getFeatures() {
        return new ArrayList<>(reservationService.getFeatures());
    }
    
    @Override
    public ReservationForm processService(ReservationForm form) {
        return reservationService.processService(form);
    }
}
