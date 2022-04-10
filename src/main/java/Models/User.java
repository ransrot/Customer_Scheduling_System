package Models;

/** Class for users in the database
 * @author Alex Bader
 * @version 1.0
 */

public class User {

    /** Username of the user */
    private String userName;

    /** Id of the user */
    private int userId;

    /** Constructs a user
     *
     * @param userName name of the user
     * @param userId id of the user
     */
    public User(String userName, int userId) {
        this.userName = userName;
        this.userId = userId;
    }

    /**
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }
}
