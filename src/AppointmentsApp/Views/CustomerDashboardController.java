package AppointmentsApp.Views;

import AppointmentsApp.Main;
import AppointmentsApp.Models.CustomerTableRow;
import AppointmentsApp.Services.CustomerService;
import AppointmentsApp.Utils.UIHelper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerDashboardController implements Initializable {

    @FXML TableView<CustomerTableRow> customerTableView;
    @FXML TableColumn<CustomerTableRow, Integer> customerTable_id;
    @FXML TableColumn<CustomerTableRow, String> customerTable_name;
    @FXML TableColumn<CustomerTableRow, String> customerTable_address;
    @FXML TableColumn<CustomerTableRow, String> customerTable_postalCode;
    @FXML TableColumn<CustomerTableRow, String> customerTable_phone;
    @FXML TableColumn<CustomerTableRow, String> customerTable_divisionName;
    @FXML TableColumn<CustomerTableRow, String> customerTable_countryName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTable_id.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        customerTable_name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        customerTable_address.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        customerTable_postalCode.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPostalCode()));
        customerTable_phone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));
        customerTable_divisionName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDivisionName()));
        customerTable_countryName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountryName()));

        var customers = CustomerService.getCustomerTableData();
        if (!customers.isEmpty()) customerTableView.setItems(customers);
    }

    public void navigateToAddCustomerForm() throws IOException {
        Parent addScene = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        Main.stage.setScene(new Scene(addScene));
        Main.stage.setTitle("Add Customer");
        Main.stage.show();
    }

    public void navigateToUpdateCustomerForm() throws IOException {
        var selectedItem = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            UIHelper.createAlert("Please select a customer to update.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateCustomer.fxml"));
            Parent root = loader.load();

            UpdateCustomerController controller = loader.getController();
            controller.setSelectedCustomer(selectedItem);

            Main.stage.setScene(new Scene(root, 600, 600));
            Main.stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the selected customer
     * @throws SQLException
     */
    public void deleteCustomer() throws SQLException {
        var selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            UIHelper.createAlert("Please select a customer to delete.", true);
            return;
        }

        if(UIHelper.createConfirmation("Are you sure you want to delete this customer?", "Delete Customer")){
            var customerID = selectedCustomer.getId();
            CustomerService.deleteCustomer(customerID);

            UIHelper.createAlert(String.format("Customer '%s' with ID '%d' has been deleted.", selectedCustomer.getName(), selectedCustomer.getId()));

            var customers = CustomerService.getCustomerTableData();
            if (!customers.isEmpty()) customerTableView.setItems(customers);
        }
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
     * Navigates the user to the Reports
     */
    public void navigateToReports() throws IOException {
        Parent addScene = FXMLLoader.load(getClass().getResource("Reports.fxml"));
        Main.stage.setScene(new Scene(addScene));
        Main.stage.setTitle("Reports");
        Main.stage.show();
    }

    /**
     * Exits the application, on button click
     */
    @FXML
    public void exit() {
        Main.closeProgram();
    }
}
