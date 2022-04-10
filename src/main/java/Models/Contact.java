package Models;

/** Contact in the DataBase
 * @author Alex Bader
 * @version 1.0
 */

public class Contact {
    /** appointment id the contact has */
    private int appointmentId;

    /** title of appointment */
    private String title;

    /** type of appointment */
    private String type;

    /** description of appointment */
    private String description;

    /** start time of appointment */
    private String startTime;

    /** end time of appointment */
    private String endTime;

    /** customer id of appointment */
    private int customerId;

    /**
     *
     * @param appointmentId id of appointment
     * @param title appointment title
     * @param type appointment type
     * @param description description type
     * @param startTime start time of appointment
     * @param endTime end time of appointment
     * @param customerId customer id of appointment
     */
    public Contact(int appointmentId, String title, String type, String description, String startTime, String endTime, int customerId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.type = type;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerId = customerId;
    }

    /**
     *
     * @return appointment id
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     *
     * @return appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return appointment type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return appointment description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return start time of appointment
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     *
     * @return end time of appointment
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     *
     * @return customer id of appointment
     */
    public int getCustomerId() {
        return customerId;
    }
}
