package AppointmentsApp.Views;

import AppointmentsApp.Main;
import AppointmentsApp.Models.Country;
import AppointmentsApp.Models.Customer;
import AppointmentsApp.Models.Division;
import AppointmentsApp.Models.User;
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

public class AddCustomerController implements Initializable {
    @FXML TextField addCustomer_id;
    @FXML TextField addCustomer_name;
    @FXML TextField addCustomer_address;
    @FXML TextField addCustomer_postalCode;
    @FXML TextField addCustomer_phone;

    @FXML ComboBox<Country> addCustomer_country;
    @FXML ComboBox<Division> addCustomer_division;

    ObservableList<Division> divisions;
    Division selectedDivision;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCountryDropdown();
        setDivisionDropdown();
    }

    /**
     * Enables the divisions dropdown and sets the dropdown values
     */
    public void enableDivisionDropdown()
    {
        divisions = DivisionService.getCountriesDivisions(addCustomer_country.getSelectionModel().getSelectedItem().getId());
        if (divisions != null)
        {
            addCustomer_division.setItems(divisions);
            addCustomer_division.setDisable(false);
        }
    }

    /**
     * Sets the values for the Division dropdown
     *
     * Lambdas used here for the purpose of increased readability and an easier implementation
     */
    private void setDivisionDropdown(){
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
            var countries = CountryService.getAllCountries();
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
    public void addCustomer() throws IOException, SQLException {
        if (validateFields())
        {
            Customer newCustomer = new Customer();
            newCustomer.setName(addCustomer_name.getText());
            newCustomer.setAddress(addCustomer_address.getText());
            newCustomer.setPhone(addCustomer_phone.getText());
            newCustomer.setPostalCode(addCustomer_postalCode.getText());

            newCustomer.setDivisionId(selectedDivision.getId());

            newCustomer.setCreatedBy(UserService.getCurrentUser().getUserName());
            newCustomer.setLastUpdatedBy(UserService.getCurrentUser().getUserName());

            CustomerService.addCustomer(newCustomer);
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
}
