package AppointmentsApp.Services;

import AppointmentsApp.Database.DBConnector;
import AppointmentsApp.Models.Customer;
import AppointmentsApp.Models.CustomerTableRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerService {
    /**
     * Returns all customers from database
     * @return
     */
    public static ObservableList<Customer> getAllCustomers()
    {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try
        {
            String sql = "select * from customers";
            var conn = DBConnector.startConnection();
            var ps = conn.prepareStatement(sql);
            ResultSet results = ps.executeQuery();

            while(results.next())
            {
                var customer = new Customer();
                customer.setId(results.getInt("Customer_Id"));
                customer.setName(results.getString("Customer_Name"));
                customer.setAddress(results.getString("Address"));
                customer.setPostalCode(results.getString("Postal_Code"));
                customer.setPhone(results.getString("Phone"));
                customer.setDivisionId(results.getInt("Division_ID"));

                customers.add(customer);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally
        {
            DBConnector.closeConnection();
        }
        return customers;
    }

    /**
     * Returns customer data for the data table in the
     * customer dashboard. Includes the Division and Country name.
     * @return
     */
    public static ObservableList<CustomerTableRow> getCustomerTableData()
    {
        ObservableList<CustomerTableRow> customers = FXCollections.observableArrayList();
        try
        {
            String sql = "select Customer_ID, Customer_Name, Address, Postal_Code, Phone,\n" +
                    "customers.Division_ID as Division_ID, Division,\n" +
                    "c.Country_ID as Country_ID, Country\n" +
                    "from customers\n" +
                    "join first_level_divisions fld\n" +
                    "on customers.Division_ID = fld.Division_ID\n" +
                    "join countries c\n" +
                    "on fld.COUNTRY_ID = c.Country_ID;";
            var conn = DBConnector.startConnection();
            var ps = conn.prepareStatement(sql);
            ResultSet results = ps.executeQuery();

            while(results.next())
            {
                var customer = new CustomerTableRow();
                customer.setId(results.getInt("Customer_Id"));
                customer.setName(results.getString("Customer_Name"));
                customer.setAddress(results.getString("Address"));
                customer.setPostalCode(results.getString("Postal_Code"));
                customer.setPhone(results.getString("Phone"));
                customer.setDivisionName(results.getString("Division"));
                customer.setCountryName(results.getString("Country"));

                customer.setDivisionId(results.getInt("Division_ID"));
                customer.setCountryId(results.getInt("Country_ID"));

                customers.add(customer);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally
        {
            DBConnector.closeConnection();
        }
        return customers;
    }

    /**
     * Deletes the selected customer
     */
    public static void deleteCustomer(int customerID) throws SQLException {
        if(AppointmentService.deleteCustomersAppointments(customerID))
        {
            String sql = "delete from customers where Customer_ID = ?";
            PreparedStatement ps = DBConnector.startConnection().prepareStatement(sql);
            ps.setInt(1, customerID);
            ps.execute();
        }
    }

    /**
     * Adds the given customer to database
     * @param customer
     */
    public static void addCustomer(Customer customer) throws SQLException {
        String insertString = ("INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?)");
        PreparedStatement ps = DBConnector.startConnection().prepareStatement(insertString);

        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        ps.setString(5, customer.getCreatedBy());
        ps.setString(6, customer.getLastUpdatedBy());
        ps.setInt(7, customer.getDivisionId());

        ps.execute();
    }

    /**
     * Updates the given customer to database
     * @param customer
     */
    public static void updateCustomer(Customer customer) throws SQLException {
        String sql = "update customers set\n" +
                "Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?,\n" +
                "Last_Updated_By = ?, Division_ID = ?\n" +
                "where Customer_ID = ?";
        PreparedStatement ps = DBConnector.startConnection().prepareStatement(sql);

        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        ps.setString(5, customer.getLastUpdatedBy());
        ps.setInt(6, customer.getDivisionId());
        ps.setInt(7, customer.getId());

        ps.execute();
    }
}
