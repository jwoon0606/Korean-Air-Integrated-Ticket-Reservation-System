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
        this.bagType = "Standard baggage";
    }
    
    public BaggageDecorator(ReservationService reservationService, int additionalBags, double bagPrice, 
                           double maxWeight, String bagType) {
        this(reservationService, additionalBags, bagPrice);
        this.maxWeight = Math.max(0, maxWeight);
        this.bagType = bagType != null ? bagType : "Standard baggage";
    }
    
    @Override
    public String getDescription() {
        String baseDesc = super.getDescription();
        String bagDesc = " + Extra baggage " + additionalBags + " bags";
        
        if (!bagType.equals("Standard baggage")) {
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
        
        String bagFeature = "Extra baggage: " + additionalBags + " bags (up to " + maxWeight + "kg each)";
        if (!bagType.equals("Standard baggage")) {
            bagFeature += " - " + bagType;
        }
        
        features.add(bagFeature);
        return features;
    }
    
    @Override
    public ReservationForm processService(ReservationForm form) {
        // 먼저 기존 서비스들을 처리
        ReservationForm processedForm = super.processService(form);
        
        // Process baggage service
        if (additionalBags > 0) {
            double totalBagCost = additionalBags * bagPrice;
            System.out.println("[Baggage Service] Extra baggage service: " + additionalBags + " bags");
            System.out.println("[Baggage Service] Baggage type: " + bagType);
            System.out.println("[Baggage Service] Max weight: " + maxWeight + "kg per bag");
            System.out.println("[Baggage Service] Total baggage fee: $" + totalBagCost);
            
            // In practice, baggage information would be added to the reservation form
            // or sent to the baggage management system
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
