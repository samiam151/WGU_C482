package AppointmentsApp.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class used to build database connection.
 * Contains methods to start, close, and get database connection.
 */
public class DBConnector {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String schema = "WJ06rEf";
    private static final String hostname = "//wgudb.ucertify.com/";

    private static final String jdbcURL = protocol + vendor + hostname + schema;

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;

    private static String username = "U06rEf";
    private static String password = "53688849557";

    /**
     * Method for starting connection to database.
     * @return Connection object for database connection
     */
    public static Connection startConnection(){

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection Successful!");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    /**
     * Method for closing connection to database when program is closed.
     */
    public static void closeConnection(){
        try {
            conn.close();
            System.out.println("Connection Closed.");
        }
        catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}