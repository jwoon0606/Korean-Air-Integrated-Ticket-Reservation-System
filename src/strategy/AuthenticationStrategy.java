package strategy;

public interface AuthenticationStrategy {
    boolean authenticate(String email, String password);
    boolean register();
    String getStrategyName();
    void displayUserInfo();
    void logout();
    boolean isLoggedIn();
    Object getCurrentUserObject();
    boolean deleteUser(String email);
    Object getLastRegisteredUser();
}