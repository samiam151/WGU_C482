package AppointmentsApp.Views;

import AppointmentsApp.Main;
import AppointmentsApp.Models.*;
import AppointmentsApp.Services.AppointmentService;
import AppointmentsApp.Services.ContactService;
import AppointmentsApp.Services.CustomerService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ReportsController implements Initializable {
    private ObservableList<AppointmentTableRow> allAppointments;
    private ObservableList<Customer> allCustomers;
    private ObservableList<Contact> allContacts;

    @FXML TextArea contactScheduleTextArea;
    @FXML TextArea appointmentsByTypeArea;
    @FXML TextArea appointmentsByMonthArea;
    @FXML TextArea appointmentsByCountryArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try
        {
            // Retrieve all necessary data
            allAppointments = AppointmentService.getAllAppointments();
            allCustomers = CustomerService.getAllCustomers();
            allContacts = ContactService.getAllContacts();

            // Set reports for text areas
            appointmentsByTypeArea.setText(appointmentsByType());
            appointmentsByMonthArea.setText(appointmentsByMonth());
            contactScheduleTextArea.setText(scheduleByContact());
            appointmentsByCountryArea.setText(appointmentsByCountry());

        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Sets the header of each division, uniformly
     * @param label
     * @param value
     * @return
     */
    public String setTitle(String label, String value)
    {
        final String dashes = "-".repeat(100);
        return String.format(dashes + "\n%s: %s\n" + dashes + "\n",
                label.toUpperCase(Locale.ROOT),
                value.toUpperCase(Locale.ROOT)
        );
    }

    /**
     * Returns string of appointments by type
     *
     * Lambdas used here for the purpose of increased readability and an easier implementation
     * @return
     */
    public String appointmentsByType()
    {
        StringBuilder returnString = new StringBuilder();
        var types = allAppointments.stream().map(apt -> apt.getType()).distinct().collect(Collectors.toList());
        types.forEach(type -> {
            returnString.append(setTitle("Type", type));
            var matchingAppointments = allAppointments.stream().filter(apt -> apt.getType().equals(type)).collect(Collectors.toList());
            returnString.append(String.format("There are %d appointments for this type.\n", matchingAppointments.stream().count()));
        });
        return returnString.toString();
    }

    /**
     * Returns string of appointments by month
     *
     * Lambdas used here for the purpose of increased readability and an easier implementation
     * @return
     */
    public String appointmentsByMonth()
    {
        StringBuilder returnString = new StringBuilder();
        var months = allAppointments.stream().map(apt -> apt.getStart().getMonth()).distinct().collect(Collectors.toList());
        months.forEach(month -> {
            returnString.append(setTitle("Month", month.toString()));
            var matchingAppointments = allAppointments.stream().filter(apt -> apt.getStart().getMonth().equals(month)).collect(Collectors.toList());
            returnString.append(String.format("There are %d appointments for this month.\n", matchingAppointments.stream().count()));
        });
        return returnString.toString();
    }

    /**
     * Method creates a string of all appointments per contact.
     *
     * Lambdas used here for the purpose of increased readability and an easier implementation
     * @return String of report data to display
     * @throws SQLException
     */
    public String scheduleByContact() throws SQLException {
        StringBuilder returnString = new StringBuilder();

        allContacts.forEach(contact -> {
            var contactsApts = allAppointments.stream()
                    .filter(apt -> apt.getContact_Name().equals(contact.getContactName()))
                    .collect(Collectors.toList());

            returnString.append(setTitle("Contact", contact.getContactName()));

            if (!contactsApts.isEmpty())
            {
                contactsApts.forEach(apt -> {
                    returnString.append(apt.toReport()+ "\n\n");
                });
            }
            else {
                returnString.append("This contact has no scheduled appointments.\n\n");
            }
        });
        return returnString.toString();
    }

    /**
     * Displays all appointments, grouped by the Customer country
     *
     * Lambdas used here for the purpose of increased readability and an easier implementation
     * @return
     */
    public String appointmentsByCountry()
    {
        StringBuilder returnString = new StringBuilder();
        var appointments = AppointmentService.getAppointmentsByCountry();
        var countries = appointments.stream().map(apt -> apt.getCountry()).distinct().collect(Collectors.toList());

        countries.forEach(country -> {
            returnString.append(setTitle("Country", country.toString()));
            var matchingAppointments = appointments.stream().filter(apt -> apt.getCountry().equals(country)).collect(Collectors.toList());
            returnString.append(String.format("There are %d appointments for this country.\n", matchingAppointments.stream().count()));
        });

        return returnString.toString();
    }

    /**
     * Navigates the user to the Appointments Dashboard
     */
    public void navigateToAppointmentDashboard() throws IOException {
        Parent addScene = FXMLLoader.load(getClass().getResource("AppointmentsDashboard.fxml"));
        Main.stage.setScene(new Scene(addScene));
        Main.stage.setTitle("Appointment Dashboard");
        Main.stage.show();
    }

    /**
     * Navigates the user to the Customer Dashboard
     */
    public void navigateToCustomerDashboard() throws IOException {
        Parent addScene = FXMLLoader.load(getClass().getResource("CustomerDashboard.fxml"));
        Main.stage.setScene(new Scene(addScene));
        Main.stage.setTitle("Customer Dashboard");
        Main.stage.show();
    }

    /**
     * Exits the program
     */
    public void exit()
    {
        Main.closeProgram();
    }
}
