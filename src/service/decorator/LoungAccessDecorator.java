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
        String loungeDesc = " + " + loungeType + " 이용";
        
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
        
        String loungeFeature = "라운지 이용: " + loungeType;
        if (!loungeLocation.isEmpty()) {
            loungeFeature += " - " + loungeLocation;
        }
        loungeFeature += " (" + validHours + "시간 이용 가능)";
        
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
        
        // 라운지 이용 서비스 처리
        System.out.println("[Lounge Access Service] 라운지 이용권 발급: " + loungeType);
        System.out.println("[Lounge Access Service] 위치: " + loungeLocation);
        System.out.println("[Lounge Access Service] 이용 시간: " + validHours + "시간");
        System.out.println("[Lounge Access Service] 이용료: $" + loungePrice);
        
        if (amenities != null && !amenities.isEmpty()) {
            System.out.println("[Lounge Access Service] 이용 가능 시설:");
            for (String amenity : amenities) {
                System.out.println("  • " + amenity);
            }
        }
        
        // 실제로는 여기서 라운지 이용권을 발급하거나
        // 라운지 관리 시스템에 고객 정보를 등록하는 로직이 들어갈 수 있음
        
        return processedForm;
    }
    
    /**
     * 라운지 타입에 따른 위치 결정
     */
    private String determineLoungeLocation(String loungeType) {
        if (loungeType == null) return "";
        
        return switch (loungeType.toLowerCase()) {
            case "비즈니스 라운지", "business lounge" -> "출발 게이트 근처";
            case "퍼스트 라운지", "first class lounge" -> "VIP 전용 구역";
            case "스카이 라운지", "sky lounge" -> "공항 중앙";
            case "프리미엄 라운지", "premium lounge" -> "면세점 인근";
            default -> "일반 구역";
        };
    }
    
    /**
     * 라운지 타입에 따른 어메니티 결정
     */
    private List<String> determineLoungeAmenities(String loungeType) {
        if (loungeType == null) return List.of();
        
        return switch (loungeType.toLowerCase()) {
            case "퍼스트 라운지", "first class lounge" -> List.of(
                "개인 스위트룸", "프리미엄 식음료", "컨시어지 서비스", 
                "스파 서비스", "비즈니스 센터", "개인 샤워실"
            );
            case "비즈니스 라운지", "business lounge" -> List.of(
                "비즈니스 식음료", "회의실", "Wi-Fi", "샤워실", "휴식 공간"
            );
            case "스카이 라운지", "sky lounge" -> List.of(
                "전망 좋은 휴식 공간", "기본 식음료", "Wi-Fi", "충전 스테이션"
            );
            default -> List.of("기본 휴식 공간", "Wi-Fi");
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
