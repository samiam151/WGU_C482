package AppointmentsApp.Views;

import AppointmentsApp.Main;
import AppointmentsApp.Models.AppointmentTableRow;
import AppointmentsApp.Services.AppointmentService;
import AppointmentsApp.Utils.UIHelper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AppointmentsDashboardController implements Initializable {

    @FXML TableView<AppointmentTableRow> appointmentsTable;
    @FXML TableColumn<AppointmentTableRow, Integer> appointmentsTable_id;
    @FXML TableColumn<AppointmentTableRow, String> appointmentsTable_description;
    @FXML TableColumn<AppointmentTableRow, String> appointmentsTable_title;
    @FXML TableColumn<AppointmentTableRow, String> appointmentsTable_location;
    @FXML TableColumn<AppointmentTableRow, String> appointmentsTable_contact;
    @FXML TableColumn<AppointmentTableRow, String> appointmentsTable_type;
    @FXML TableColumn<AppointmentTableRow, LocalDateTime> appointmentsTable_start;
    @FXML TableColumn<AppointmentTableRow, LocalDateTime> appointmentsTable_end;
    @FXML TableColumn<AppointmentTableRow, Integer> appointmentsTable_customerId;

    @FXML ToggleGroup appointmentDateView;

    @FXML Button appointments_addButton;
    @FXML Button appointments_updateButton;
    @FXML Button appointments_deleteButton;

    ObservableList<AppointmentTableRow> appointments = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the table column settings
        appointmentsTable_id.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointment_ID()).asObject());
        appointmentsTable_title.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        appointmentsTable_description.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        appointmentsTable_location.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        appointmentsTable_contact.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContact_Name()));
        appointmentsTable_type.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        appointmentsTable_start.setCellValueFactory(new PropertyValueFactory<>("Start"));
        appointmentsTable_end.setCellValueFactory(new PropertyValueFactory<>("End"));
        appointmentsTable_customerId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCustomer_ID()).asObject());

        // Set the appointments for the table
        appointments = AppointmentService.getUsersAppointmentsForTable();
        if (!appointments.isEmpty()) {
            appointmentsTable.setItems(appointments);
        }

        // Displays the upcoming appointments to the user
        displayAppointmentReminders();

        // Add appointment time range view listeners
        appointmentDateView.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                ObservableList<AppointmentTableRow> newAppointments = FXCollections.observableArrayList();

                RadioButton checked = (RadioButton)appointmentDateView.getSelectedToggle();
                String value = checked.getText();

                switch (value) {
                    case "Week":
                        appointments = getAppointmentsForWeek();
                        break;
                    case "Month":
                        appointments = getAppointmentsForMonth();
                        break;
                    default:
                        appointments = AppointmentService.getUsersAppointmentsForTable();
                        break;
                }
                if (appointments != null) {
                    appointmentsTable.setItems(appointments);
                }
            }
        });
    }

    /**
     * Displays the upcoming appointments to the user
     */
    public void displayAppointmentReminders()
    {
        var upcomingAppointments = appointments.stream()
                .filter(apt ->
                        apt.getStart().isBefore(LocalDateTime.now().plusMinutes(15))
                                && apt.getStart().isAfter(LocalDateTime.now())
                )
                .collect(Collectors.toList());
        if (upcomingAppointments.stream().count() > 0)
        {
            var message = new StringBuilder();
            var numAppts = upcomingAppointments.stream().count();
            message.append(String.format("You have %d %s within 15 minutes.", numAppts, numAppts > 1 ? "appointments" : "appointment"));
            message.append("\n-----------------------\n");

            upcomingAppointments.forEach(apt -> {
                message.append(String.format("Appointment ID: %d\n", apt.getAppointment_ID()));
                message.append(String.format("Appointment Date: %s\n", apt.getStart().toLocalDate()));
                message.append(String.format("Appointment Time: %s\n", apt.getStart().toLocalTime()));
                message.append("-----------------------\n");
            });

            UIHelper.createAlert(message.toString());
        }
        else
        {
            String message = String.format("You have no appointments within 15 minutes.");
            UIHelper.createAlert(message);
        }
    }

    /**
     * Filters the appointments that occur with the month
     * @return
     */
    private ObservableList<AppointmentTableRow> getAppointmentsForMonth()
    {
        ObservableList<AppointmentTableRow> newAppointments = FXCollections.observableArrayList();
        var _appointments = AppointmentService.getUsersAppointmentsForTable();
        _appointments.stream()
                .filter(appointment -> {
                    return appointment.getStart().isAfter(LocalDateTime.now()) && appointment.getStart().isBefore(LocalDateTime.now().plusMonths(1));
                })
                .forEach(appointment -> newAppointments.add(appointment));
        return newAppointments;
    }

    /**
     * Filters the appointments that occur within the week
     * @return
     */
    private ObservableList<AppointmentTableRow> getAppointmentsForWeek()
    {
        ObservableList<AppointmentTableRow> newAppointments = FXCollections.observableArrayList();
        var _appointments = AppointmentService.getUsersAppointmentsForTable();
        _appointments.stream()
                .filter(appointment -> {
                    return appointment.getStart().isAfter(LocalDateTime.now()) && appointment.getStart().isBefore(LocalDateTime.now().plusWeeks(1));
                })
                .forEach(appt -> newAppointments.add(appt));
        return newAppointments;
    }

    /**
     * Handles the button click to add an appointment
     * @throws IOException
     */
    @FXML
    public void addAppointment() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddAppointment.fxml"));
        Main.stage.setTitle("Add Appointment");
        Main.stage.setScene(new Scene(root, 900, 600));
        Main.stage.show();
    }

    /**
     * Handles the button click to update an appointment
     */
    @FXML
    public void updateAppointment() {
        if (appointmentsTable.getSelectionModel().getSelectedItem() == null)
        {
            UIHelper.createAlert("Please select an appointment to update.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateAppointment.fxml"));
            Parent root = loader.load();

            UpdateAppointmentController controller = loader.getController();
            AppointmentTableRow selectedAppt = appointmentsTable.getSelectionModel().getSelectedItem();
            controller.setSelectedItem(selectedAppt);


            Main.stage.setScene(new Scene(root, 900, 600));
            Main.stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the button click to delete an appointment
     */
    @FXML
    public void deleteAppointment() {
        var selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            UIHelper.createAlert("Please select an appointment to delete.", true);
            return;
        }

        if(UIHelper.createConfirmation("Are you sure you want to delete this appointment?", "Delete Appointment")){
            var appointmentId = selectedAppointment.getAppointment_ID();
            AppointmentService.deleteAppointment(appointmentId);

            UIHelper.createAlert(String.format("Appointment '%d' of type '%s' has been canceled",
                selectedAppointment.getAppointment_ID(),
                selectedAppointment.getType()
            ));

            appointments = AppointmentService.getUsersAppointmentsForTable();
            if (appointments != null) {
                appointmentsTable.setItems(appointments);
            }
        }
    }

    /**
     * Exits the application, on button click
     */
    @FXML
    public void exit() {
        Main.closeProgram();
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
     * Navigates the user to the Reports
     */
    public void navigateToReports() throws IOException {
        Parent addScene = FXMLLoader.load(getClass().getResource("Reports.fxml"));
        Main.stage.setScene(new Scene(addScene));
        Main.stage.setTitle("Reports");
        Main.stage.show();
    }


}
