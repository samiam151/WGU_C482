package AppointmentsApp.Models;

public class Country {
    private Integer Id;
    private String Name;

    /**
     * Returns the country id
     * @return
     */
    public Integer getId() {
        return Id;
    }

    /**
     * Sets the country id
     * @param id
     */
    public void setId(Integer id) {
        Id = id;
    }

    /**
     * Returns the country name
     * @return
     */
    public String getName() {
        return Name;
    }

    /**
     * Sets the country name
     * @param name
     */
    public void setName(String name) {
        Name = name;
    }
}
