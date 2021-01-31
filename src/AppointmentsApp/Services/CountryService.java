package AppointmentsApp.Services;

import AppointmentsApp.Database.DBConnector;
import AppointmentsApp.Models.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryService {

    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> countries = FXCollections.observableArrayList();

        String sql = "select * from countries";
        var conn = DBConnector.startConnection();
        var ps = conn.prepareStatement(sql);
        ResultSet results = ps.executeQuery();

        while(results.next())
        {
            Country _country = new Country();
            _country.setId(results.getInt("Country_ID"));
            _country.setName(results.getString("Country"));
            countries.add(_country);
        }
        return countries;
    }
}
