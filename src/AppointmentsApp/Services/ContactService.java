package AppointmentsApp.Services;

import AppointmentsApp.Database.DBConnector;
import AppointmentsApp.Models.Contact;
import AppointmentsApp.Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class ContactService {
    /**
     * Returns all contacts from database
     * @return
     */
    public static ObservableList<Contact> getAllContacts()
    {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();

        try
        {
            String sql = "select * from contacts";
            var conn = DBConnector.startConnection();
            var ps = conn.prepareStatement(sql);
            ResultSet results = ps.executeQuery();

            while(results.next()) {
                var contact = new Contact();
                contact.setContactId(results.getInt("Contact_ID"));
                contact.setContactName(results.getString("Contact_Name"));
                contact.setContactEmail(results.getString("Email"));

                contacts.add(contact);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally
        {
            DBConnector.closeConnection();
        }
        return contacts;
    }
}
