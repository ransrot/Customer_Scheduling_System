package Models;

/** Represents appointments table in database
 * @author Alex Bader
 * @version 1.0
 */

public class Appointments {
    /** id of appointment */
    private int id;

    /** title of appointment */
    private String title;

    /** description of appointment */
    private String description;

    /** location of appointment */
    private String location;

    /** contact id */
    private int contactId;

    /** type of appointment */
    private String type;

    /** start date and time of appointment */
    private String startDateAndTime;

    /** end date and time of appointment */
    private String endDateAndTime;

    /** customer id of appointment */
    private int customerId;

    /** user id of appointment */
    private int userId;

    /**
     *
     * @param id id of appointment
     * @param title title of appointment
     * @param description description of appointment
     * @param location location of appointment
     * @param contactId contact id of appointment
     * @param type type of appointment
     * @param startDateAndTime start date and time of appointment
     * @param endDateAndTime end date and time of appointment
     * @param customerId customer id of appointment
     * @param userId user id of appointment
     */
    public Appointments(int id, String title, String description, String location, int contactId,
                        String type, String startDateAndTime, String endDateAndTime, int customerId, int userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactId = contactId;
        this.type = type;
        this.startDateAndTime = startDateAndTime;
        this.endDateAndTime = endDateAndTime;
        this.customerId = customerId;
        this.userId = userId;
    }

    /**
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id set id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title set title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description set description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location set location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     *
     * @param contactId set contact id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type set type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return start date and time
     */
    public String getStartDateAndTime() {
        return startDateAndTime;
    }

    /**
     *
     * @param startDateAndTime set startDateAndTime
     */
    public void setStartDateAndTime(String startDateAndTime) {
        this.startDateAndTime = startDateAndTime;
    }

    /**
     *
     * @return endDateAndTime
     */
    public String getEndDateAndTime() {
        return endDateAndTime;
    }

    /**
     *
     * @param endDateAndTime set endDateAndTime
     */
    public void setEndDateAndTime(String endDateAndTime) {
        this.endDateAndTime = endDateAndTime;
    }

    /**
     *
     * @return the customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     *
     * @param customerId set customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     *
     * @return user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @param userId set userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
