package AppointmentsApp.Models;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class AppointmentTableRow {
    private int Appointment_ID;
    private String Title;
    private String Description;
    private String Location;
    private String Type;
    private LocalDateTime Start;
    private LocalDateTime End;
    private Integer Customer_ID;
    private Integer User_ID;
    private String Contact_Name;

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
    public LocalDateTime getStart() {
        return Start;
    }

    /**
     * Sets the appointment start time
     * @param start
     */
    public void setStart(LocalDateTime start) {
        Start = start;
    }

    /**
     * Returns the appointment end time
     * @return
     */
    public LocalDateTime getEnd() {
        return End;
    }

    /**
     * Sets the appointment end time
     * @param end
     */
    public void setEnd(LocalDateTime end) {
        End = end;
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
     * Returns the Contact name
     * @return
     */
    public String getContact_Name() {
        return Contact_Name;
    }

    /**
     * Sets the Contact name
     * @param contact_Name
     */
    public void setContact_Name(String contact_Name) {
        Contact_Name = contact_Name;
    }

    /**
     * Returns a string representation of the appointment for the Reports
     * @return
     */
    public String toReport()
    {
        return "Appointment ID: " + this.getAppointment_ID() +
                "\n\tTitle: " + this.getTitle() +
                "\n\tType: " + this.getType() +
                "\n\tDescription: " + this.getDescription() +
                "\n\tStart: " + this.getStart() +
                "\n\tEnd: " + this.getEnd() +
                "\n\tCustomer ID: " + this.getCustomer_ID();
    }
}
