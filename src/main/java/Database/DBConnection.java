package Database;

import java.sql.Connection;
import java.sql.DriverManager;

/** The connection to the database
 * @author Alex Bader
 * @version 1.0
 */

public abstract class DBConnection {

    /** protocol of the database */
    private static final String protocol = "jdbc";

    /** vendor of the database */
    private static final String vendor = ":mysql:";

    /** location of where its stored */
    private static final String location = "//localhost/";

    /** name of the database */
    private static final String databaseName = "client_schedule";

    /** the jdbc url to login */
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";

    /** the driver we use */
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    /** username of the database */
    private static final String userName = "sqlUser";

    /** password of the database */
    private static final String password = "Passw0rd!";

    /** the connection to the database */
    public static Connection connection;

    /**
     * Opens a connection to the database
     */
    public static void openConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the connection to the database
     */
    public static void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
        }
    }

    /**
     *
     * @return the connection
     */
    public static Connection getConnection() {
        return connection;
    }
}
