package AppointmentsApp.Models;

public class User {
    private String userName;
    private String password;
    private Integer id;

    /**
     * Returns the user name
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the user password
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user id
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the user id
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
