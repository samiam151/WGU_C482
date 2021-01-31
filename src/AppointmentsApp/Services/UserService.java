package AppointmentsApp.Services;

import AppointmentsApp.Database.DBConnector;
import AppointmentsApp.Models.User;
import AppointmentsApp.Models.UserDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private static User currentUser = null;

    /**
     * Returns the current user
     * @return
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user
     * @param user
     */
    public static void setCurrentUser(UserDTO user) {
        User newUser = new User();
        newUser.setUserName(user.userName);
        newUser.setPassword(user.password);
        newUser.setId(user.id);
        currentUser = newUser;
    }

    /**
     * Given a username and password, performs a DB search for a match;
     * If none is found, returns null
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public static UserDTO searchUsers(String username, String password) throws SQLException {
        UserDTO user = null;

        try {
            String sql = String.format("select * from users where User_Name = '%s' and Password = '%s'", username, password);
            var conn = DBConnector.startConnection();
            var ps = conn.prepareStatement(sql);
            ResultSet results = ps.executeQuery();

            while(results.next()) {
                user = new UserDTO();
                user.userName = results.getString("User_Name");
                user.password = results.getString("Password");
                user.id = results.getInt("User_Id");
            }
            DBConnector.closeConnection();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return user;
    }

    /**
     * Returns all users from database
     * @return
     */
    public static ObservableList<User> getAllUsers()
    {
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            String sql = "select * from users";
            var conn = DBConnector.startConnection();
            var ps = conn.prepareStatement(sql);
            ResultSet results = ps.executeQuery();

            while(results.next()) {
                var user = new User();
                user.setUserName(results.getString("User_Name"));
                user.setPassword(results.getString("Password"));
                user.setId(results.getInt("User_Id"));

                users.add(user);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally
        {
            DBConnector.closeConnection();
        }
        return users;
    }

    /**
     * Logs out the current user
     */
    public static void logout() {
        currentUser = null;
    }
}
