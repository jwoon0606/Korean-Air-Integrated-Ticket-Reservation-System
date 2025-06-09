package controller;

import state.Bright;
import state.ThemeState;

public class ThemeController {
    private ThemeState state;

    public ThemeController() {
        state = Bright.getInstance();
    }

    public void setState(ThemeState state) {
        this.state = state;
    }

    public void switchState() {
        state.switchThemeMode(this);
    }

    public void alertState() {
        state.AlertThemeMode();
    }
}
