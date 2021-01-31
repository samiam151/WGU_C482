package AppointmentsApp.Utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationUtils {
    private static Locale currentLocale;

    /**
     * Returns the current user's locale
     * @return
     */
    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    /**
     * Sets the current user's local
     * @param locale
     */
    public static void setCurrentLocale(Locale locale) {
        currentLocale = locale;
    }

    /**
     * Returns the resource bundle for the Login page
     * @return
     */
    public static ResourceBundle Login() {
        return ResourceBundle.getBundle("AppointmentsApp.Localization.Login", currentLocale);
    }
}
