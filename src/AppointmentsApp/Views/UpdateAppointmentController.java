package AppointmentsApp.Views;

import AppointmentsApp.Main;
import AppointmentsApp.Models.*;
import AppointmentsApp.Services.AppointmentService;
import AppointmentsApp.Services.ContactService;
import AppointmentsApp.Services.CustomerService;
import AppointmentsApp.Services.UserService;
import AppointmentsApp.Utils.TimeUtils;
import AppointmentsApp.Utils.UIHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class UpdateAppointmentController implements Initializable {
    private AppointmentTableRow selectedItem;

    @FXML TextField addAppointment_idField;
    @FXML TextField addAppointment_titleField;
    @FXML TextField addAppointment_DescriptionField;
    @FXML TextField addAppointment_LocationField;
    @FXML TextField addAppointment_typeField;

    @FXML DatePicker addAppointment_dateField;
    @FXML ComboBox<LocalTime> addAppointment_startTimeField;
    @FXML ComboBox<LocalTime> addAppointment_endTimeField;

    @FXML ComboBox<String> addAppointment_customerField;
    @FXML ComboBox<String> addAppointment_UserField;
    @FXML ComboBox<String> addAppointment_contactField;

    ObservableList<User> users = null;
    ObservableList<Contact> contacts = null;
    ObservableList<Customer> customers = null;

    /**
     * Lambdas used here for the purpose of increased readability and an easier implementation
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set User items by name
        users = UserService.getAllUsers();
        var userItems = FXCollections.observableList(users.stream().map(user -> user.getUserName()).collect(Collectors.toList()));
        addAppointment_UserField.setItems(userItems);

        // Set Contact items by name
        contacts = ContactService.getAllContacts();
        var contactItems = FXCollections.observableList(contacts.stream().map(contact -> contact.getContactName()).collect(Collectors.toList()));
        addAppointment_contactField.setItems(contactItems);

        // Set Customer items by name
        customers = CustomerService.getAllCustomers();
        var customerItems = FXCollections.observableList(customers.stream().map(customer -> customer.getName()).collect(Collectors.toList()));
        addAppointment_customerField.setItems(customerItems);

        // Set start and end time dates
        LocalDateTime localStart = TimeUtils.getLocalStartTime();
        LocalDateTime localEnd = TimeUtils.getLocalEndTime();

        while (localStart.isBefore(localEnd.minusHours(1).plusSeconds(1))){
            addAppointment_startTimeField.getItems().add(LocalTime.from(localStart));
            addAppointment_endTimeField.getItems().add(LocalTime.from(localStart).plusHours(1));
            localStart = localStart.plusMinutes(30);
        }
    }

    /**
     * Validates the selected date
     */
    public Boolean validateDate()
    {
        LocalDate selectedDate = addAppointment_dateField.getValue();
        if (selectedDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) || selectedDate.getDayOfWeek().equals(DayOfWeek.SUNDAY))
        {
            UIHelper.createAlert("Please select a weekday.");
            return false;
        }
        return true;
    }

    /**
     * Validates the selected date and time
     *
     * Lambdas used here for the purpose of increased readability and an easier implementation
     */
    public Boolean validateTime()
    {
        LocalDate selectedDate = addAppointment_dateField.getValue();
        var startTime = addAppointment_startTimeField.getValue();
        var endTime = addAppointment_endTimeField.getValue();

        var conflictingAppointments = AppointmentService.getAllAppointments()
                .stream().filter(appt -> {
                    if (appt.getStart().toLocalDate().equals(selectedDate))
                    {
                        var apptStartTime = appt.getStart().toLocalTime();
                        var apptEndTime = appt.getEnd().toLocalTime();

                        if (startTime.isAfter(apptStartTime) && startTime.isBefore(apptEndTime) ||
                                endTime.isAfter(apptStartTime) && endTime.isBefore(apptEndTime) ||
                                startTime.isBefore(apptStartTime) && endTime.isAfter(apptEndTime) ||
                                startTime.equals(apptStartTime) && endTime.equals(apptEndTime) ||
                                startTime.equals(apptStartTime) || endTime.equals(apptEndTime)
                        ){
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());

        if (conflictingAppointments.stream().count() > 0)
        {
            UIHelper.createAlert("This time conflicts with another appointment. Please choose another time.");
            return false;
        }
        return true;
    }

    /**
     * Adjusts the end time field in accordance to the start time field
     */
    @FXML
    public void adjustEndTime()
    {
        LocalTime selectedStart = addAppointment_startTimeField.getSelectionModel().getSelectedItem();
        addAppointment_endTimeField.setValue(selectedStart.plusHours(1));

        if (!validateTime()) return;

        addAppointment_endTimeField.getItems().clear();

        while (selectedStart.isBefore(TimeUtils.getLocalEndTime().toLocalTime()))
        {
            var newStart = selectedStart.plusMinutes(30);
            addAppointment_endTimeField.getItems().add(newStart);
            selectedStart = newStart;
        }

        addAppointment_endTimeField.setValue(addAppointment_startTimeField.getSelectionModel().getSelectedItem().plusHours(1));
    }

    /**
     * Sets the default values in the form fields from the selected appointment
     *
     * Lambdas used here for the purpose of increased readability and an easier implementation
     */
    private void setDefaultFormValues() {
        // Set text fields
        addAppointment_idField.setText(String.valueOf(selectedItem.getAppointment_ID()));
        addAppointment_titleField.setText(selectedItem.getTitle());
        addAppointment_DescriptionField.setText(selectedItem.getDescription());
        addAppointment_LocationField.setText(selectedItem.getLocation());
        addAppointment_typeField.setText(selectedItem.getType());

        // Set date and time fields
        addAppointment_dateField.setValue(selectedItem.getStart().toLocalDate());
        addAppointment_startTimeField.setValue(selectedItem.getStart().toLocalTime());
        addAppointment_endTimeField.setValue(selectedItem.getEnd().toLocalTime());

        // Set dropdown fields
        var customer = customers.stream()
                .filter(cust -> cust.getId() == selectedItem.getCustomer_ID())
                .findFirst()
                .get();
        addAppointment_customerField.setValue(customer.getName());

        var user = users.stream()
                .filter(_user -> _user.getId() == selectedItem.getUser_ID())
                .findFirst()
                .get();
        addAppointment_UserField.setValue(user.getUserName());
        addAppointment_contactField.setValue(selectedItem.getContact_Name());
    }

    /**
     * Sets the selected appointment for the controller
     * @param selectedItem
     */
    public void setSelectedItem(AppointmentTableRow selectedItem) {
        this.selectedItem = selectedItem;
        setDefaultFormValues();
    }

    /**
     * Handles the button click to update the selected appointment
     *
     * Lambdas used here for the purpose of increased readability and an easier implementation
     */
    @FXML
    public void updateAppointment() {
        if(!validationPassed()) return;

        try
        {
            var newAppointment = new Appointment();
            newAppointment.setAppointment_ID(Integer.valueOf(addAppointment_idField.getText()));
            newAppointment.setTitle(addAppointment_titleField.getText());
            newAppointment.setDescription(addAppointment_DescriptionField.getText());
            newAppointment.setLocation(addAppointment_LocationField.getText());
            newAppointment.setType(addAppointment_typeField.getText());

            newAppointment.setStart(addAppointment_startTimeField.getValue());
            newAppointment.setEnd(addAppointment_endTimeField.getValue());

            var selectedContactName = addAppointment_contactField.getSelectionModel().getSelectedItem();
            var matchingContact = contacts.stream().filter(contact -> contact.getContactName().equals(selectedContactName)).findFirst().get();
            newAppointment.setContact_ID(matchingContact.getContactId());

            var selectedCustomerName = addAppointment_customerField.getSelectionModel().getSelectedItem();
            var matchingCustomer = customers.stream().filter(customer -> customer.getName().equals(selectedCustomerName)).findFirst().get();
            newAppointment.setCustomer_ID(matchingCustomer.getId());

            var selectedUserName = addAppointment_UserField.getSelectionModel().getSelectedItem();
            var matchingUser = users.stream().filter(user -> user.getUserName().equals(selectedUserName)).findFirst().get();
            newAppointment.setUser_ID(matchingUser.getId());
            newAppointment.setCreated_By(matchingUser.getUserName());
            newAppointment.setLast_Updated_By(matchingUser.getUserName());

            AppointmentService.updateAppointment(newAppointment, addAppointment_dateField.getValue());

            Main.goToAppointmentsDashboard(getClass());
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            UIHelper.createAlert("Appointment could not be updated.", true);
        }
    }

    /**
     * Checks for empty or invlaid fields before updating an appointment
     *
     * Lambdas used here for the purpose of increased readability and an easier implementation
     * @return
     */
    private boolean validationPassed() {
        try {
            List<TextField> textFields = Arrays.asList(addAppointment_titleField, addAppointment_DescriptionField,
                    addAppointment_LocationField, addAppointment_typeField);

            if (textFields.stream().anyMatch(field -> field.getText().isEmpty())) {
                UIHelper.createAlert("All appointment fields must be filled in.", true);
                return false;
            }

            if (addAppointment_startTimeField.getValue() == null || addAppointment_endTimeField.getValue() == null) {
                UIHelper.createAlert("Appointment start and end time must be selected.", true);
                return false;
            }

            List<ComboBox<String>> dropdownFields = Arrays.asList(addAppointment_UserField, addAppointment_customerField, addAppointment_contactField);
            if (dropdownFields.stream().anyMatch(field -> field.getSelectionModel().getSelectedItem() == null)) {
                UIHelper.createAlert("User, contact, and customer values must be selected.", true);
                return false;
            }

            if(!validateDate()) return false;
            return validateTime();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return false;
    }

    /**
     * Handles Cancel button click, returns to the Appointment Dashboard
     * @throws IOException
     */
    @FXML
    public void cancel() throws IOException {
        var confirmation = UIHelper.createConfirmation("Are you sure you want to cancel creating an appointment?", "Cancel");
        if (confirmation)
        {
            Parent addScene = FXMLLoader.load(getClass().getResource("AppointmentsDashboard.fxml"));
            Main.stage.setScene(new Scene(addScene));
            Main.stage.setTitle("Appointments Dashboard");
            Main.stage.show();
        }
    }
}
