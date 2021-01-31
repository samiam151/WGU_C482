package AppointmentsApp.Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class UIHelper {
    /**
     * Generic method to create an alert box
     * @param message
     */
    public static void createAlert(String message) {
        createAlert(message, false);
    }

    /**
     * Generic method to create an alert box
     * @param message
     */
    public static void createAlert(String message, Boolean error) {
        Alert.AlertType type = error ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION;
        Alert alert = new Alert(type);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(error ? "Error" : "Information");
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * Creates a confirmation modal, returns the boolean result
     * @param message
     * @param title
     * @return
     */
    public static Boolean createConfirmation(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.setHeaderText("Do you wish to continue?");
        alert.setTitle(title);

        Optional<ButtonType> result =  alert.showAndWait();
        return result.get() == ButtonType.OK;
    }
}
