package state;

import controller.ThemeController;

public class Dark implements ThemeState {
    private static Dark dark = new Dark();
    private Dark() {}

    public static Dark getInstance() {
        return dark;
    }
    @Override
    public void switchThemeMode(ThemeController themeController) {
        System.out.println("Switch to Bright Mode...");
        themeController.setState(Bright.getInstance());
    }

    @Override
    public void AlertThemeMode() {
        System.out.println("\n\n☽ Dark Mode ☾");
    }
}
