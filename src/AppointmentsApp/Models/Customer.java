package AppointmentsApp.Models;

import java.time.LocalDateTime;

/**
 * Customer is the entity used for modeling
 * customer objects from the database
 */
public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String divisionName;
    private int divisionId;

    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

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
     * @return current customer create date as LocalDateTime
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate customer create date to set
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * @return user who created customer by as string
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy user who created customer to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return current last update as LocalDateTime
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate last update to set
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return current user who last updated customer
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * @param lastUpdatedBy user who last updated customer to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @return current customer division ID as int
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * @param divisionId division ID to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
