package service.decorator;

import dto.ReservationForm;
import service.ReservationService;
import java.util.List;

/**
 * 라운지 이용 서비스 데코레이터
 * 데코레이터 패턴의 ConcreteDecorator로 추가적인 럭셔리 서비스 제공
 */
public class LoungAccessDecorator extends ReservationServiceDecorator {
    private String loungeType;
    private double loungePrice;
    private String loungeLocation;
    private List<String> amenities;
    private int validHours;
    
    public LoungAccessDecorator(ReservationService reservationService, String loungeType, double loungePrice) {
        super(reservationService);
        this.loungeType = loungeType != null ? loungeType : "일반 라운지";
        this.loungePrice = Math.max(0, loungePrice);
        this.loungeLocation = determineLoungeLocation(loungeType);
        this.amenities = determineLoungeAmenities(loungeType);
        this.validHours = determineValidHours(loungeType);
    }
    
    public LoungAccessDecorator(ReservationService reservationService, String loungeType, 
                               double loungePrice, String loungeLocation) {
        this(reservationService, loungeType, loungePrice);
        this.loungeLocation = loungeLocation != null ? loungeLocation : this.loungeLocation;
    }
    
    @Override
    public String getDescription() {
        String baseDesc = super.getDescription();
        String loungeDesc = " + " + loungeType + " access";
        
        if (!loungeLocation.isEmpty()) {
            loungeDesc += " (" + loungeLocation + ")";
        }
        
        return baseDesc + loungeDesc;
    }
    
    @Override
    public double calculatePrice() {
        return super.calculatePrice() + loungePrice;
    }
    
    @Override
    public List<String> getFeatures() {
        List<String> features = super.getFeatures();
        
        String loungeFeature = "Lounge access: " + loungeType;
        if (!loungeLocation.isEmpty()) {
            loungeFeature += " - " + loungeLocation;
        }
        loungeFeature += " (" + validHours + " hours available)";
        
        features.add(loungeFeature);
        
        // 라운지 어메니티 추가
        if (amenities != null && !amenities.isEmpty()) {
            for (String amenity : amenities) {
                features.add("  • " + amenity);
            }
        }
        
        return features;
    }
    
    @Override
    public ReservationForm processService(ReservationForm form) {
        // 먼저 기존 서비스들을 처리
        ReservationForm processedForm = super.processService(form);
        
        // Process lounge access service
        System.out.println("[Lounge Access Service] Lounge pass issued: " + loungeType);
        System.out.println("[Lounge Access Service] Location: " + loungeLocation);
        System.out.println("[Lounge Access Service] Access hours: " + validHours + " hours");
        System.out.println("[Lounge Access Service] Fee: $" + loungePrice);
        
        if (amenities != null && !amenities.isEmpty()) {
            System.out.println("[Lounge Access Service] Available facilities:");
            for (String amenity : amenities) {
                System.out.println("  • " + amenity);
            }
        }
        
        // In practice, lounge passes would be issued
        // or customer information would be registered in the lounge management system
        
        return processedForm;
    }
    
    /**
     * 라운지 타입에 따른 위치 결정
     */
    private String determineLoungeLocation(String loungeType) {
        if (loungeType == null) return "";
        
        return switch (loungeType.toLowerCase()) {
            case "비즈니스 라운지", "business lounge" -> "Near departure gates";
            case "퍼스트 라운지", "first class lounge" -> "VIP exclusive area";
            case "스카이 라운지", "sky lounge" -> "Airport central";
            case "프리미엄 라운지", "premium lounge" -> "Near duty-free shops";
            default -> "General area";
        };
    }
    
    /**
     * 라운지 타입에 따른 어메니티 결정
     */
    private List<String> determineLoungeAmenities(String loungeType) {
        if (loungeType == null) return List.of();
        
        return switch (loungeType.toLowerCase()) {
            case "퍼스트 라운지", "first class lounge" -> List.of(
                "Private suite rooms", "Premium food & beverages", "Concierge service", 
                "Spa services", "Business center", "Private shower rooms"
            );
            case "비즈니스 라운지", "business lounge" -> List.of(
                "Business refreshments", "Meeting rooms", "Wi-Fi", "Shower facilities", "Rest areas"
            );
            case "스카이 라운지", "sky lounge" -> List.of(
                "Scenic rest areas", "Basic refreshments", "Wi-Fi", "Charging stations"
            );
            default -> List.of("Basic rest area", "Wi-Fi");
        };
    }
    
    /**
     * 라운지 타입에 따른 이용 가능 시간 결정
     */
    private int determineValidHours(String loungeType) {
        if (loungeType == null) return 3;
        
        return switch (loungeType.toLowerCase()) {
            case "퍼스트 라운지", "first class lounge" -> 6;
            case "비즈니스 라운지", "business lounge" -> 4;
            case "프리미엄 라운지", "premium lounge" -> 4;
            default -> 3;
        };
    }
    
    // Getter methods
    public String getLoungeType() {
        return loungeType;
    }
    
    public double getLoungePrice() {
        return loungePrice;
    }
    
    public String getLoungeLocation() {
        return loungeLocation;
    }
    
    public List<String> getAmenities() {
        return amenities;
    }
    
    public int getValidHours() {
        return validHours;
    }
}
