package components;

import java.util.prefs.Preferences;

public class Preference {

    static Preferences userPreferences = Preferences.userNodeForPackage(Preference.class);
    private static final String DARK_MODE = "Preference.darkMode";
    private static final String LIGHT_MODE = "Preference.lightMode";
    private static final String DELIVERY_DURATION = "Preference.deliveryDuration";

    public static boolean isDarkMode() {
        return userPreferences.getBoolean(DARK_MODE, false);
    }

    public static boolean isLightMode() {
        return userPreferences.getBoolean(LIGHT_MODE, true);
    }

    public static int getDeliveryDuration() {
        return userPreferences.getInt(DELIVERY_DURATION, 5);
    }

    public static void updateDarkMode(boolean value) {
        userPreferences.putBoolean(DARK_MODE, value);
    }

    public static void updateLightMode(boolean value) {
        userPreferences.putBoolean(LIGHT_MODE, value);
    }

    public static void updateDeliveryDuration(int value) {
        userPreferences.putInt(DELIVERY_DURATION, value);
    }
}