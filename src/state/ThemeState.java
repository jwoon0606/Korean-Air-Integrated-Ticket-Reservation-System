package state;

import controller.ThemeController;

public interface ThemeState {
    public void switchThemeMode(ThemeController themeController);
    public void AlertThemeMode();
}
