package AppointmentsApp.Models;

import java.time.LocalDateTime;

public class CustomerTableRow {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String divisionName;
    private String countryName;

    private int divisionId;
    private int countryId;

    /**
     * @return current customer division name as string
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * @param divisionName division name to set
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * @return current customer id as int
     */
    public int getId() {
        return id;
    }

    /**
     * @param id customer id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return current customer name as string
     */
    public String getName() {
        return name;
    }

    /**
     * @param name customer name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return current customer address as string
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address customer address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return current customer postal code as string
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode customer postal code to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return current customer phone as string
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone customer phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Return country name
     * @return
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Set the country name
     * @param countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Get the country ID
     * @return
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Set the country ID
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Get the division ID
     * @return
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Set the division ID
     * @param divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
