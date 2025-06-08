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
        String mealFeature = "In-flight meal: " + mealType;
        
        if (!specialRequirements.isEmpty()) {
            mealFeature += " (Special request: " + specialRequirements + ")";
        }
        
        features.add(mealFeature);
        return features;
    }
    
    @Override
    public ReservationForm processService(ReservationForm form) {
        // Process existing services first
        ReservationForm processedForm = super.processService(form);
        
        // Process meal service
        System.out.println("[Meal Service] In-flight meal service added: " + mealType + " ($" + mealPrice + ")");
        
        if (!specialRequirements.isEmpty()) {
            System.out.println("[Meal Service] Special requirements: " + specialRequirements);
        }
        
        // In practice, meal information would be added to the reservation form
        // or meal orders would be sent to external systems
        
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
