package AppointmentsApp.Services;

import AppointmentsApp.Database.DBConnector;
import AppointmentsApp.Models.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DivisionService {
    public static ObservableList<Division> getAllDivisions()
    {
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        try
        {
            String sql = "select * from first_level_divisions";
            PreparedStatement ps = DBConnector.startConnection().prepareStatement(sql);
            ResultSet results = ps.executeQuery();

            while (results.next())
            {
                var division = new Division();
                division.setName(results.getString("Division"));
                division.setId(results.getInt("Division_ID"));
                division.setCountry_Id(results.getInt("Country_ID"));
                divisions.add(division);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            DBConnector.closeConnection();
        }
        return divisions;
    }

    public static ObservableList<Division> getCountriesDivisions(int countryID)
    {
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        try
        {
            String sql = "select * from first_level_divisions where Country_ID = ?";
            PreparedStatement ps = DBConnector.startConnection().prepareStatement(sql);
            ps.setInt(1, countryID);
            ResultSet results = ps.executeQuery();

            while (results.next())
            {
                var division = new Division();
                division.setName(results.getString("Division"));
                division.setId(results.getInt("Division_ID"));
                division.setCountry_Id(results.getInt("Country_ID"));
                divisions.add(division);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            DBConnector.closeConnection();
        }
        return divisions;
    }
}
