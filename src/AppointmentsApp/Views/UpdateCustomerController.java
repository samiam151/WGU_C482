package AppointmentsApp.Views;

import AppointmentsApp.Main;
import AppointmentsApp.Models.Country;
import AppointmentsApp.Models.Customer;
import AppointmentsApp.Models.CustomerTableRow;
import AppointmentsApp.Models.Division;
import AppointmentsApp.Services.CountryService;
import AppointmentsApp.Services.CustomerService;
import AppointmentsApp.Services.DivisionService;
import AppointmentsApp.Services.UserService;
import AppointmentsApp.Utils.UIHelper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {
    @FXML TextField addCustomer_id;
    @FXML TextField addCustomer_name;
    @FXML TextField addCustomer_address;
    @FXML TextField addCustomer_postalCode;
    @FXML TextField addCustomer_phone;

    @FXML ComboBox<Country> addCustomer_country;
    @FXML ComboBox<Division> addCustomer_division;

    private CustomerTableRow selectedCustomer;
    ObservableList<Division> divisions;
    ObservableList<Country> countries;
    Division selectedDivision;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Sets default values for the customer table
     *
     * Lambdas used here for the purpose of increased readability and an easier implementation
     */
    public void setDefaultValues()
    {
        addCustomer_id.setText(String.valueOf(selectedCustomer.getId()));
        addCustomer_name.setText(selectedCustomer.getName());
        addCustomer_address.setText(selectedCustomer.getAddress());
        addCustomer_postalCode.setText(selectedCustomer.getPostalCode());
        addCustomer_phone.setText(selectedCustomer.getPhone());

        var defaultCountry = countries.stream().filter(c -> c.getId() == selectedCustomer.getCountryId()).findFirst().get();
        addCustomer_country.setValue(defaultCountry);

        var defaultDivision = divisions.stream().filter(c -> c.getId() == selectedCustomer.getDivisionId()).findFirst().get();
        addCustomer_division.setValue(defaultDivision);
    }

    /**
     * Enables the divisions dropdown and sets the dropdown values
     */
    public void resetDivisionDropdown()
    {
        addCustomer_division.setValue(null);
        divisions = DivisionService.getCountriesDivisions(addCustomer_country.getSelectionModel().getSelectedItem().getId());
        if (divisions != null)
        {
            addCustomer_division.setItems(divisions);
        }
    }

    /**
     * Sets the values for the Division dropdown
     *
     * Lambdas used here for the purpose of increased readability and an easier implementation
     */
    private void setDivisionDropdown(){
        divisions = DivisionService.getCountriesDivisions(selectedCustomer.getCountryId());
        addCustomer_division.setItems(divisions);
        addCustomer_division.setConverter(new StringConverter<Division>() {
            @Override
            public String toString(Division division) {
                if (division == null) return null;
                selectedDivision = division;
                return division.getName();
            }

            @Override
            public Division fromString(String s) {
                var divisions = DivisionService.getAllDivisions();
                var thisDivision = divisions.stream().filter(d -> d.getName().equals(s)).findFirst().get();
                System.out.println(thisDivision.getName());
                return thisDivision;
            }
        });
    }

    /**
     * Sets the values for the Country dropdown
     *
     * Lambdas used here for the purpose of increased readability and an easier implementation
     */
    private void setCountryDropdown()
    {
        try {
            countries = CountryService.getAllCountries();
            addCustomer_country.setItems(countries);
            addCustomer_country.setConverter(new StringConverter<Country>(){
                @Override
                public String toString(Country country) {
                    if (country == null) return null;
                    return country.getName();
                }

                @Override
                public Country fromString(String s) {
                    return countries.stream().filter(c -> c.getName().equals(s)).findFirst().get();
                }
            });
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Saves the new customer
     * @throws IOException
     */
    @FXML
    public void updateCustomer() throws IOException, SQLException {
        if (validateFields())
        {
            Customer _customer = new Customer();
            _customer.setId(Integer.valueOf(addCustomer_id.getText()));
            _customer.setName(addCustomer_name.getText());
            _customer.setAddress(addCustomer_address.getText());
            _customer.setPhone(addCustomer_phone.getText());
            _customer.setPostalCode(addCustomer_postalCode.getText());

            _customer.setDivisionId(selectedDivision.getId());

            _customer.setLastUpdatedBy(UserService.getCurrentUser().getUserName());

            CustomerService.updateCustomer(_customer);
            navigateToCustomerDashboard();
        }
    }

    /**
     * Validates the customer field inputs
     *
     * Lambdas used here for the purpose of increased readability and an easier implementation
     * @return
     */
    public boolean validateFields()
    {
        List<TextField> textFieldList = Arrays.asList(addCustomer_name, addCustomer_address,
                addCustomer_postalCode, addCustomer_phone);

        if (textFieldList.stream().anyMatch(field -> field.getText().isEmpty()))
        {
            UIHelper.createAlert("All customer fields must be filled.", true);
            return false;
        }

        if (addCustomer_country.getValue() == null || addCustomer_division.getValue() == null)
        {
            UIHelper.createAlert("Country and division fields must be filled.", true);
            return false;
        }
        return true;
    }

    /**
     * Cancels the creating of a customer
     * @throws IOException
     */
    @FXML
    public void cancel() throws IOException {
        var confirmation = UIHelper.createConfirmation("Are you sure you want to cancel creating a customer?", "Cancel");
        if (confirmation)
        {
            navigateToCustomerDashboard();
        }
    }

    /**
     * Returns the user back to the Customer Dashboard
     * @throws IOException
     */
    private void navigateToCustomerDashboard() throws IOException {
        Parent addScene = FXMLLoader.load(getClass().getResource("CustomerDashboard.fxml"));
        Main.stage.setScene(new Scene(addScene));
        Main.stage.setTitle("Customer Dashboard");
        Main.stage.show();
    }

    /**
     * Sets the selected customer for the controller
     * @param selectedCustomer
     */
    public void setSelectedCustomer(CustomerTableRow selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
        setCountryDropdown();
        setDivisionDropdown();

        setDefaultValues();
    }
}
