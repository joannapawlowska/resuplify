package components;

import java.util.prefs.Preferences;

public class Preference {

    private static final Preferences userPreferences = Preferences.userNodeForPackage(Preference.class);
    public static final String DARK_MODE = "Preference.darkMode";
    public static final String DELIVERY_DURATION = "Preference.deliveryDuration";

    public static boolean isDarkMode() {
        return userPreferences.getBoolean(DARK_MODE, false);
    }

    public static int getDeliveryDuration() {
        return userPreferences.getInt(DELIVERY_DURATION, 5);
    }

    public static void updateDarkMode(boolean value) {
        userPreferences.putBoolean(DARK_MODE, value);
    }

    public static void updateDeliveryDuration(int value) {
        userPreferences.putInt(DELIVERY_DURATION, value);
    }

    public static Preferences getUserPreferences(){ return userPreferences; }
}