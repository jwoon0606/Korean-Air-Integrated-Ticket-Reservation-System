package service.decorator;

import dto.ReservationForm;
import service.ReservationService;

import java.util.List;

/**
 * 기내식 서비스 데코레이터
 * 데코레이터 패턴의 ConcreteDecorator로 도로의 LineDecorator 역할
 */
public class MealDecorator extends ReservationServiceDecorator {
    private String mealType;
    private double mealPrice;
    private String specialRequirements;
    
    public MealDecorator(ReservationService reservationService, String mealType, double mealPrice) {
        super(reservationService);
        this.mealType = mealType != null ? mealType : "기본 기내식";
        this.mealPrice = mealPrice;
        this.specialRequirements = "";
    }
    
    public MealDecorator(ReservationService reservationService, String mealType, double mealPrice, String specialRequirements) {
        this(reservationService, mealType, mealPrice);
        this.specialRequirements = specialRequirements != null ? specialRequirements : "";
    }
    
    @Override
    public String getDescription() {
        String baseDesc = super.getDescription();
        String mealDesc = " + " + mealType;
        
        if (!specialRequirements.isEmpty()) {
            mealDesc += " (" + specialRequirements + ")";
        }
        
        return baseDesc + mealDesc;
    }
    
    @Override
    public double calculatePrice() {
        return super.calculatePrice() + mealPrice;
    }
    
    @Override
    public List<String> getFeatures() {
        List<String> features = super.getFeatures();
        String mealFeature = "기내식: " + mealType;
        
        if (!specialRequirements.isEmpty()) {
            mealFeature += " (특별요청: " + specialRequirements + ")";
        }
        
        features.add(mealFeature);
        return features;
    }
    
    @Override
    public ReservationForm processService(ReservationForm form) {
        // 먼저 기존 서비스들을 처리
        ReservationForm processedForm = super.processService(form);
        
        // 기내식 서비스 처리
        System.out.println("[Meal Service] 기내식 서비스 추가: " + mealType + " ($" + mealPrice + ")");
        
        if (!specialRequirements.isEmpty()) {
            System.out.println("[Meal Service] 특별 요청사항: " + specialRequirements);
        }
        
        // 실제로는 여기서 예약 폼에 기내식 정보를 추가하거나 
        // 외부 시스템에 기내식 주문을 전송하는 로직이 들어갈 수 있음
        
        return processedForm;
    }
    
    // Getter methods
    public String getMealType() {
        return mealType;
    }
    
    public double getMealPrice() {
        return mealPrice;
    }
    
    public String getSpecialRequirements() {
        return specialRequirements;
    }
}
