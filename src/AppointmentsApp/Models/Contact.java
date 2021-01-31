package AppointmentsApp.Models;

public class Contact {
    private Integer contactId;
    private String contactName;
    private String contactEmail;

    /**
     * Returns the contact id
     * @return
     */
    public Integer getContactId() {
        return contactId;
    }

    /**
     * Sets the contact id
     * @param contactId
     */
    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    /**
     * Returns the contact name
     * @return
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the contact name
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Returns the contact email
     * @return
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Sets the contact email
     * @param contactEmail
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
