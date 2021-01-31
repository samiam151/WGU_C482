package AppointmentsApp.Models;

public class AppointmentWithCountry extends AppointmentTableRow{
    private String Country;

    /**
     * Returns the country of the appointment customer
     * @return
     */
    public String getCountry() {
        return Country;
    }

    /**
     * Sets the country of the appointment customer
     * @param country
     */
    public void setCountry(String country) {
        Country = country;
    }
}
