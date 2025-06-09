package command.concrete_command;
import command.undo_command.UndoableCommand;
import controller.LoginController;
import controller.ThemeController;

/**
 * 사용자 정보 조회를 위한 명령 구현체
 */
public class ThemeSwitchCommand implements UndoableCommand {
    private ThemeController themeController;

    public ThemeSwitchCommand(ThemeController themeController) {
        this.themeController = themeController;
    }
    
    @Override
    public void execute() {
        themeController.switchState();
    }
    
    @Override
    public boolean canExecute(boolean isLoggedIn) {
        return true;
    }
    
    @Override
    public String getMenuText() {
        return "15. Switch Theme mode(dark/bright)";
    }

    @Override
    public void undo() {
        System.out.println("Returning the Theme Mode.");
        themeController.switchState();
    }
}