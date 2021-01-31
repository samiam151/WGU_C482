package AppointmentsApp.Models;

import java.time.LocalTime;

public class Appointment {
    private int Appointment_ID;
    private String Title;
    private String Description;
    private String Location;
    private String Type;
    private LocalTime Start;
    private LocalTime End;
    private String Created_By;
    private String Last_Updated_By;
    private Integer Customer_ID;
    private Integer User_ID;
    private Integer Contact_ID;

    /**
     * Returns the appointment ID
     * @return
     */
    public int getAppointment_ID() {
        return Appointment_ID;
    }

    /**
     * Sets the appointment ID
     * @param appointment_ID
     */
    public void setAppointment_ID(int appointment_ID) {
        Appointment_ID = appointment_ID;
    }

    /**
     * Returns the appointment title
     * @return
     */
    public String getTitle() {
        return Title;
    }

    /**
     * Sets the appointment title
     * @param title
     */
    public void setTitle(String title) {
        Title = title;
    }

    /**
     * Returns the appointment description
     * @return
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Sets the appointment description
     * @param description
     */
    public void setDescription(String description) {
        Description = description;
    }

    /**
     * Returns the appointment location
     * @return
     */
    public String getLocation() {
        return Location;
    }

    /**
     * Sets the appointment location
     * @param location
     */
    public void setLocation(String location) {
        Location = location;
    }

    /**
     * Returns the appointment type
     * @return
     */
    public String getType() {
        return Type;
    }

    /**
     * Sets the appointment type
     * @param type
     */
    public void setType(String type) {
        Type = type;
    }

    /**
     * Returns the appointment start time
     * @return
     */
    public LocalTime getStart() {
        return Start;
    }

    /**
     * Sets the appointment start time
     * @param start
     */
    public void setStart(LocalTime start) {
        Start = start;
    }

    /**
     * Returns the appointment end time
     * @return
     */
    public LocalTime getEnd() {
        return End;
    }

    /**
     * Sets the appointment end time
     * @param end
     */
    public void setEnd(LocalTime end) {
        End = end;
    }

    /**
     * Returns the created-by field
     * @return
     */
    public String getCreated_By() {
        return Created_By;
    }

    /**
     * Sets the created-by field
     * @param created_By
     */
    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    /**
     * Returns the last updated by field
     * @return
     */
    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    /**
     * Sets the last updated by field
     * @param last_Updated_By
     */
    public void setLast_Updated_By(String last_Updated_By) {
        Last_Updated_By = last_Updated_By;
    }

    /**
     * Returns the customer ID
     * @return
     */
    public Integer getCustomer_ID() {
        return Customer_ID;
    }

    /**
     * Sets the customer ID
     * @param customer_ID
     */
    public void setCustomer_ID(Integer customer_ID) {
        Customer_ID = customer_ID;
    }

    /**
     * Returns the User ID
     * @return
     */
    public Integer getUser_ID() {
        return User_ID;
    }

    /**
     * Sets the User ID
     * @param user_ID
     */
    public void setUser_ID(Integer user_ID) {
        User_ID = user_ID;
    }

    /**
     * Returns the contact ID
     * @return
     */
    public Integer getContact_ID() {
        return Contact_ID;
    }

    /**
     * Sets the contact ID
     * @param contact_ID
     */
    public void setContact_ID(Integer contact_ID) {
        Contact_ID = contact_ID;
    }
}
