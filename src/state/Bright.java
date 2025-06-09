package state;

import controller.ThemeController;

public class Bright implements ThemeState{
    private static Bright bright = new Bright();
    private Bright() {}

    public static Bright getInstance() {
        return bright;
    }
    @Override
    public void switchThemeMode(ThemeController themeController) {
        System.out.println("Switch to Dark Mode...");
        themeController.setState(Dark.getInstance());
    }

    @Override
    public void AlertThemeMode() {
        System.out.println("\n\n☀☀ Bright Mode ☀☀");
    }
}
