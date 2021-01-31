package AppointmentsApp;

import AppointmentsApp.Utils.LocalizationUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class Main extends Application {
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;

        // Set Locale for app
        // Locale.setDefault(Locale.FRENCH);
        Locale userLocale = Locale.getDefault();
        LocalizationUtils.setCurrentLocale(userLocale);

        // Show the Login form
        Parent root = FXMLLoader.load(getClass().getResource("Views/Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void goToAppointmentsDashboard(Class _this) throws IOException {
        Parent addScene = FXMLLoader.load(_this.getResource("AppointmentsDashboard.fxml"));
        Main.stage.setScene(new Scene(addScene));
        Main.stage.setTitle("Appointments Dashboard");
        Main.stage.show();
    }

    /**
     * Exits the program
     */
    public static void closeProgram() {
        stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
