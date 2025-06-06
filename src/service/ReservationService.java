package service;

import dto.ReservationForm;
import java.util.List;

/**
 * 예약 서비스 Component 인터페이스
 * 데코레이터 패턴의 Component 역할로 도로의 Road 인터페이스 역할
 */
public interface ReservationService {
    
    /**
     * 서비스 설명 반환
     * @return 서비스에 대한 설명
     */
    String getDescription();
    
    /**
     * 총 가격 계산
     * @return 계산된 총 가격
     */
    double calculatePrice();
    
    /**
     * 서비스에 포함된 기능 목록 반환
     * @return 기능 목록
     */
    List<String> getFeatures();
    
    /**
     * 예약 서비스 처리
     * @param form 처리할 예약 폼
     * @return 처리된 예약 폼
     */
    ReservationForm processService(ReservationForm form);
}