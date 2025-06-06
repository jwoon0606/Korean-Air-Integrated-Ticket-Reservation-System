package service.decorator;

import dto.ReservationForm;
import service.ReservationService;
import java.util.List;

/**
 * 수하물 서비스 데코레이터
 * 데코레이터 패턴의 ConcreteDecorator로 도로의 TrafficMonitorDecorator 역할
 */
public class BaggageDecorator extends ReservationServiceDecorator {
    private int additionalBags;
    private double bagPrice;
    private double maxWeight;
    private String bagType;
    
    public BaggageDecorator(ReservationService reservationService, int additionalBags, double bagPrice) {
        super(reservationService);
        this.additionalBags = Math.max(0, additionalBags);
        this.bagPrice = Math.max(0, bagPrice);
        this.maxWeight = 23.0; // 기본 최대 중량 23kg
        this.bagType = "일반 수하물";
    }
    
    public BaggageDecorator(ReservationService reservationService, int additionalBags, double bagPrice, 
                           double maxWeight, String bagType) {
        this(reservationService, additionalBags, bagPrice);
        this.maxWeight = Math.max(0, maxWeight);
        this.bagType = bagType != null ? bagType : "일반 수하물";
    }
    
    @Override
    public String getDescription() {
        String baseDesc = super.getDescription();
        String bagDesc = " + 추가수하물 " + additionalBags + "개";
        
        if (!bagType.equals("일반 수하물")) {
            bagDesc += " (" + bagType + ")";
        }
        
        return baseDesc + bagDesc;
    }
    
    @Override
    public double calculatePrice() {
        return super.calculatePrice() + (additionalBags * bagPrice);
    }
    
    @Override
    public List<String> getFeatures() {
        List<String> features = super.getFeatures();
        
        String bagFeature = "추가 수하물: " + additionalBags + "개 (각 " + maxWeight + "kg 이하)";
        if (!bagType.equals("일반 수하물")) {
            bagFeature += " - " + bagType;
        }
        
        features.add(bagFeature);
        return features;
    }
    
    @Override
    public ReservationForm processService(ReservationForm form) {
        // 먼저 기존 서비스들을 처리
        ReservationForm processedForm = super.processService(form);
        
        // 수하물 서비스 처리
        if (additionalBags > 0) {
            double totalBagCost = additionalBags * bagPrice;
            System.out.println("[Baggage Service] 추가 수하물 서비스: " + additionalBags + "개");
            System.out.println("[Baggage Service] 수하물 타입: " + bagType);
            System.out.println("[Baggage Service] 최대 중량: " + maxWeight + "kg/개");
            System.out.println("[Baggage Service] 총 수하물 요금: $" + totalBagCost);
            
            // 실제로는 여기서 수하물 정보를 예약 폼에 추가하거나
            // 수하물 관리 시스템에 정보를 전송하는 로직이 들어갈 수 있음
        }
        
        return processedForm;
    }
    
    /**
     * 수하물 총 비용 계산
     */
    public double getTotalBaggageCost() {
        return additionalBags * bagPrice;
    }
    
    /**
     * 수하물 허용 총 중량 계산
     */
    public double getTotalAllowedWeight() {
        return additionalBags * maxWeight;
    }
    
    // Getter methods
    public int getAdditionalBags() {
        return additionalBags;
    }
    
    public double getBagPrice() {
        return bagPrice;
    }
    
    public double getMaxWeight() {
        return maxWeight;
    }
    
    public String getBagType() {
        return bagType;
    }
}
