package service.decorator;

import dto.ReservationForm;
import service.ReservationService;
import java.util.List;

/**
 * Lounge Access Service Decorator
 * ConcreteDecorator in the Decorator Pattern for providing additional luxury services
 */
public class LoungAccessDecorator extends ReservationServiceDecorator {
    private String loungeType;
    private double loungePrice;
    private String loungeLocation;
    private List<String> amenities;
    private int validHours;
    
    public LoungAccessDecorator(ReservationService reservationService, String loungeType, double loungePrice) {
        super(reservationService);
        this.loungeType = loungeType != null ? loungeType : "Standard Lounge";
        this.loungePrice = Math.max(0, loungePrice);
        // Simplified: hardcoded for demo/testing
        this.loungeLocation = "General area";
        this.amenities = List.of("Basic rest area", "Wi-Fi");
        this.validHours = 3;
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

        // Add lounge amenities
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
