package components.logic;

import java.util.prefs.Preferences;

public class Preference {

    private static final Preferences userPreferences = Preferences.userNodeForPackage(Preference.class);
    public static final String DARK_MODE = "Preference.darkMode";
    public static final String DELIVERY_DURATION = "Preference.deliveryDuration";
    public static final String TOKEN = "Preference.token";

    public static boolean isDarkMode() {
        return userPreferences.getBoolean(DARK_MODE, false);
    }

    public static int getDeliveryDuration() {
        return userPreferences.getInt(DELIVERY_DURATION, 5);
    }

    public static String getToken() {
        return userPreferences.get(TOKEN, "");
    }

    public static void updateDarkMode(boolean value) {
        userPreferences.putBoolean(DARK_MODE, value);
    }

    public static void updateDeliveryDuration(int value) {
        userPreferences.putInt(DELIVERY_DURATION, value);
    }

    public static void updateToken(String value) {
        userPreferences.put(TOKEN, value);
    }

    public static void clearToken() {
        userPreferences.remove(TOKEN);
    }
}