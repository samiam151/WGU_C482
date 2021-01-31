package AppointmentsApp.Views;

import AppointmentsApp.Main;
import AppointmentsApp.Models.UserDTO;
import AppointmentsApp.Services.UserService;
import AppointmentsApp.Utils.LocalizationUtils;
import AppointmentsApp.Utils.UIHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML private Label login_countryLabel;
    @FXML private TextField login_countryField;
    @FXML private TextField login_usernameField;
    @FXML private PasswordField login_passwordField;
    @FXML private Label login_usernameLabel;
    @FXML private Label login_passwordLabel;
    @FXML private Button login_button;

    /**
     * Finds the language of the user, then sets the labels and messaging
     * to that language
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setInternationLabels();
    }

    /**
     * Sets the field labels according to the user's language
     */
    private void setInternationLabels() {
        login_countryLabel.setText(LocalizationUtils.Login().getString("CountryLabel"));
        login_countryField.setText(LocalizationUtils.Login().getString("Country"));
        login_usernameLabel.setText(LocalizationUtils.Login().getString("Username"));
        login_passwordLabel.setText(LocalizationUtils.Login().getString("Password"));
        login_button.setText(LocalizationUtils.Login().getString("Login"));
    }

    /**
     * Attempts to authenticate the user. If user not found,
     * shows the user an error message
     * @throws SQLException
     */
    public void login() throws SQLException, IOException {
        var username = login_usernameField.getText();
        var password = login_passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            UIHelper.createAlert(LocalizationUtils.Login().getString("NotFilled"), true);
            return;
        }

        UserDTO user = UserService.searchUsers(username, password);

        if (user != null) {
            UserService.setCurrentUser(user);
            logLoginAttempt(true);
            Main.goToAppointmentsDashboard(getClass());
        }
        else {
            UIHelper.createAlert(LocalizationUtils.Login().getString("Error"), true);
            logLoginAttempt(false);
        }
    }

    /**
     * Logs to file the result of every login attempt
     * @param success
     */
    private void logLoginAttempt(boolean success) {
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        final String time = formatter.format(OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        try {
            final FileWriter fw = new FileWriter("login_activity.txt", true);
            final BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Time: " + time + "\t");
            bw.write("Username: " + login_usernameField.getText() + "\t");
            bw.write("Result: " + (success ? "Login successful" : "Login unsuccessful") + "\t");
            bw.newLine();
            bw.close();

            System.out.println("Login attempt successfully logged.");
        } catch (IOException ex) {
            System.out.println("Failed to log invalid login attempt:");
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Runs the login function when the user hits the enter key
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    private void handleEnter(KeyEvent event) throws IOException, SQLException {
        if (event.getCode() == KeyCode.ENTER) {
            login();
        }
    }
}
