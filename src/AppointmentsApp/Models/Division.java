package AppointmentsApp.Models;

public class Division {
    private int Id;
    private String Name;
    private int Country_Id;

    /**
     * Returns the division id
     * @return
     */
    public int getId() {
        return Id;
    }

    /**
     * Sets the division id
     * @param id
     */
    public void setId(int id) {
        Id = id;
    }

    /**
     * Returns the division name
     * @return
     */
    public String getName() {
        return Name;
    }

    /**
     * Sets the division name
     * @param name
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * Returns the country id
     * @return
     */
    public int getCountry_Id() {
        return Country_Id;
    }

    /**
     * Sets the country id
     * @param country_Id
     */
    public void setCountry_Id(int country_Id) {
        Country_Id = country_Id;
    }
}
