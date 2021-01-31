package AppointmentsApp.Services;

import AppointmentsApp.Database.DBConnector;
import AppointmentsApp.Models.Appointment;
import AppointmentsApp.Models.AppointmentTableRow;

import AppointmentsApp.Models.AppointmentWithCountry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AppointmentService {

    /**
     * Returns all available appointments
     * @return
     */
    public static ObservableList<AppointmentTableRow> getAllAppointments()
    {
        ObservableList<AppointmentTableRow> appointments = FXCollections.observableArrayList();
        try {
            String selectStatement = String.format("SELECT * FROM appointments\n" +
                    "JOIN contacts \n" +
                    "ON appointments.Contact_ID = contacts.Contact_ID;");

            var conn = DBConnector.startConnection();
            var ps = conn.prepareStatement(selectStatement);
            ResultSet results = ps.executeQuery();

            while(results.next()) {
                var appt = new AppointmentTableRow();
                appt.setAppointment_ID(results.getInt("Appointment_Id"));

                appt.setTitle(results.getString("Title"));
                appt.setDescription(results.getString("Description"));
                appt.setLocation(results.getString("Location"));
                appt.setContact_Name(results.getString("Contact_Name"));
                appt.setType(results.getString("Type"));
                appt.setStart(results.getTimestamp("Start").toLocalDateTime());
                appt.setEnd(results.getTimestamp("End").toLocalDateTime());
                appt.setCustomer_ID(results.getInt("Customer_ID"));
                appt.setUser_ID(results.getInt("User_ID"));

                appointments.add(appt);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            DBConnector.closeConnection();
        }

        return appointments;
    }

    /**
     * Returns appointment data for the current user to feed to the dashboard table
     * @return
     */
    public static ObservableList<AppointmentTableRow> getUsersAppointmentsForTable()
    {
        ObservableList<AppointmentTableRow> appointments = FXCollections.observableArrayList();
        try {
            String selectStatement = String.format("SELECT * FROM appointments\n" +
                    "JOIN contacts \n" +
                    "ON appointments.Contact_ID = contacts.Contact_ID \n" +
                    "where appointments.User_ID = %d", UserService.getCurrentUser().getId());

            var conn = DBConnector.startConnection();
            var ps = conn.prepareStatement(selectStatement);
            ResultSet results = ps.executeQuery();

            while(results.next()) {
                var appt = new AppointmentTableRow();
                appt.setAppointment_ID(results.getInt("Appointment_Id"));

                appt.setTitle(results.getString("Title"));
                appt.setDescription(results.getString("Description"));
                appt.setLocation(results.getString("Location"));
                appt.setContact_Name(results.getString("Contact_Name"));
                appt.setType(results.getString("Type"));
                appt.setStart(results.getTimestamp("Start").toLocalDateTime());
                appt.setEnd(results.getTimestamp("End").toLocalDateTime());
                appt.setCustomer_ID(results.getInt("Customer_ID"));
                appt.setUser_ID(results.getInt("User_ID"));

                appointments.add(appt);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            DBConnector.closeConnection();
        }

        return appointments;
    }

    /**
     * Deletes a given appointment
     * @param apptId
     */
    public static void deleteAppointment(int apptId) {
        try
        {
            String sql = String.format("delete from appointments where Appointment_Id = %d", apptId);
            var conn = DBConnector.startConnection();
            var ps = conn.prepareStatement(sql);
            ps.execute();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            DBConnector.closeConnection();
        }
    }

    /**
     * Saves  given appointment
     *
     * Lambda used here to get the latest appointment
     *
     * @param newAppointment
     * @param date
     */
    public static AppointmentTableRow addAppointment(Appointment newAppointment, LocalDate date) {
        try
        {
            String sql = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID, Created_By, Last_Updated_By)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = DBConnector.startConnection().prepareStatement(sql);

            var start = LocalDateTime.of(date, newAppointment.getStart());
            var end = LocalDateTime.of(date, newAppointment.getEnd());

            ps.setString(1, newAppointment.getTitle());
            ps.setString(2, newAppointment.getDescription());
            ps.setString(3, newAppointment.getLocation());
            ps.setString(4, newAppointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, newAppointment.getCustomer_ID());
            ps.setInt(8, newAppointment.getUser_ID());
            ps.setInt(9, newAppointment.getContact_ID());
            ps.setString(10, newAppointment.getCreated_By());
            ps.setString(11, newAppointment.getLast_Updated_By());

            ps.execute();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            DBConnector.closeConnection();
        }

        return getAllAppointments().stream().reduce((first, second) -> second)
                .orElse(null);
    }

    /**
     * Updates a given appointment
     * @param newAppointment
     * @param date
     */
    public static void updateAppointment(Appointment newAppointment, LocalDate date) {
        try
        {
            String sql = "update appointments set \n" +
                    "Title = ?,\n" +
                    "Description = ?,\n" +
                    "Location = ?,\n" +
                    "Type = ?,\n" +
                    "Start = ?,\n" +
                    "End = ?,\n" +
                    "Customer_ID = ?,\n" +
                    "User_ID = ?,\n" +
                    "Contact_ID = ?,\n" +
                    "Last_Updated_By = ?\n" +
                    "where Appointment_ID = ?";

            PreparedStatement ps = DBConnector.startConnection().prepareStatement(sql);

            var start = LocalDateTime.of(date, newAppointment.getStart());
            var end = LocalDateTime.of(date, newAppointment.getEnd());

            ps.setString(1, newAppointment.getTitle());
            ps.setString(2, newAppointment.getDescription());
            ps.setString(3, newAppointment.getLocation());
            ps.setString(4, newAppointment.getType());
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, newAppointment.getCustomer_ID());
            ps.setInt(8, newAppointment.getUser_ID());
            ps.setInt(9, newAppointment.getContact_ID());
            ps.setString(10, newAppointment.getLast_Updated_By());
            ps.setInt(11, newAppointment.getAppointment_ID());

            ps.execute();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            DBConnector.closeConnection();
        }
    }

    /**
     * Deletes the appointments associated with a given customer
     * @param customerID
     * @return
     */
    public static boolean deleteCustomersAppointments(int customerID)
    {
        boolean succeeded = false;
        try
        {
            String sql = "delete from appointments where Customer_ID = ?";
            PreparedStatement ps = DBConnector.startConnection().prepareStatement(sql);
            ps.setInt(1, customerID);
            ps.execute();

            succeeded = true;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            succeeded = false;
        }
        finally {
            DBConnector.closeConnection();
        }
        return succeeded;
    }

    /**
     * Returns appointments with Country data
     * @return
     */
    public static ObservableList<AppointmentWithCountry> getAppointmentsByCountry()
    {
        ObservableList<AppointmentWithCountry> appointments = FXCollections.observableArrayList();
        try {
            String selectStatement = String.format("select Appointment_ID, Country, Type, Title, Description, Start, End, c.Customer_ID\n" +
                    "from appointments\n" +
                    "    join customers c on c.Customer_ID = appointments.Customer_ID\n" +
                    "    join first_level_divisions fld on fld.Division_ID = c.Division_ID\n" +
                    "    join countries c2 on c2.Country_ID = fld.COUNTRY_ID;");

            var conn = DBConnector.startConnection();
            var ps = conn.prepareStatement(selectStatement);
            ResultSet results = ps.executeQuery();

            while(results.next()) {
                var appt = new AppointmentWithCountry();
                appt.setAppointment_ID(results.getInt("Appointment_Id"));

                appt.setTitle(results.getString("Title"));
                appt.setDescription(results.getString("Description"));
                appt.setType(results.getString("Type"));
                appt.setStart(results.getTimestamp("Start").toLocalDateTime());
                appt.setEnd(results.getTimestamp("End").toLocalDateTime());
                appt.setCustomer_ID(results.getInt("Customer_ID"));
                appt.setCountry(results.getString("Country"));

                appointments.add(appt);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            DBConnector.closeConnection();
        }

        return appointments;
    }
}
