package Utils;

import Database.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Useful SQL commands to use for the DataBase
 * @author Alex Bader
 * @version 1.0
 */

public abstract class SqlCommand {

    /** Gets the resultset after executing a query
     *
     * @param sql the sql command to execute
     * @return the resultset from the query
     */
    public static ResultSet getResultSet(String sql) {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /** Gets the preparedstatement from a sql string
     *
     * @param sql the sql command to execute
     * @return the preparedstatement from the query
     */
    public static PreparedStatement getPreparedStatement(String sql) {
        try {
            return DBConnection.getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /** Gets the division ID from the state
     *
     * @param state the state in the database
     * @return the division id of that state
     */
    public static int getDivisionId(String state) {
        String sql = "SELECT Division_ID\n" +
                "\tFROM first_level_divisions\n" +
                "    WHERE Division = '" + state + "'";

        ResultSet rs = SqlCommand.getResultSet(sql);

        if (rs == null)
            return 0;

        int id = 0;

        try {
            while (rs.next())
                id = rs.getInt("Division_ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    /** Gets the state given a division id
     *
     * @param divisionId the id in the database
     * @return the state after executing query
     */
    public static String getState(int divisionId) {
        String sql = "SELECT Division\n" +
                "\tFROM first_level_divisions\n" +
                "    WHERE Division_ID = " + divisionId;
        ResultSet rs = getResultSet(sql);

        if (rs == null)
            return "";

        try {
            rs.next();
            return rs.getString("Division");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    /** Gets the country
     *
     * @param divisionId the id in the database
     * @return the country after executing query
     */
    public static String getCountry(int divisionId) {
        String sql = "SELECT Country\n" +
                "\tFROM first_level_divisions\n" +
                "    INNER JOIN countries\n" +
                "    ON first_level_divisions.COUNTRY_ID = countries.Country_ID\n" +
                "    WHERE Division_ID = " + divisionId;
        ResultSet rs = getResultSet(sql);

        if (rs == null)
            return "";

        try {
            rs.next();
            return rs.getString("Country");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    /** Gets the country id
     *
     * @param country the country in the database
     * @return the id of the country after executing query
     */
    public static int getCountryId(String country) {
        String sql = "SELECT Country_ID FROM countries WHERE Country = '" + country + "'";
        ResultSet rs = SqlCommand.getResultSet(sql);
        int id = 0;

        try {
            rs.next();
            id = rs.getInt("Country_ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    /** Gets the next auto_increment from the table
     *
     * @param tableName the name of the table in the database
     * @return the next auto_increment in the table
     */
    public static int getNextAutoIncrementTable(String tableName) {
        String sql = "SELECT AUTO_INCREMENT\n" +
                "\tFROM information_schema.tables\n" +
                "    WHERE table_name = '" + tableName + "'  AND table_schema = DATABASE();";
        ResultSet rs = getResultSet(sql);

        if (rs == null)
            return 0;

        try {
            rs.next();
            return rs.getInt("AUTO_INCREMENT");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /** Gets the contact ID given the contact name
     *
     * @param name name inside the database
     * @return the contact id after executing query
     */
    public static int getContactId(String name) {
        String sql = "SELECT Contact_ID\n" +
                "\tFROM contacts\n" +
                "    WHERE '" + name + "' = Contact_Name";
        ResultSet rs = getResultSet(sql);

        if (rs == null)
            return 0;

        try {
            rs.next();
            return rs.getInt("Contact_ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /** Check if a customer exists in a table given ID
     *
     * @param id id of the customer
     * @return whether or not the customer exists
     */
    public static boolean checkCustomerExists(int id) {
        String sql = "SELECT * FROM customers WHERE Customer_ID = " + id;
        ResultSet rs = getResultSet(sql);

        if (rs == null)
            return false;

        try {
            if (!rs.next())
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    /** Deletes appointments given a customer id
     *
     * @param customerId the id of the customer in the table
     */
    public static void deleteAppointmentsWithCustomer(int customerId) {
        String sql = "DELETE FROM appointments WHERE Customer_ID = " + customerId;
        PreparedStatement ps = getPreparedStatement(sql);

        if (ps == null)
            return;

        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Deletes a customer given a customer id
     *
     * @param customerId the id of the customer in the table
     */
    public static void deleteCustomerWithCustomerId(int customerId) {
        String sql = "DELETE FROM customers WHERE Customer_ID = " + customerId;
        PreparedStatement ps = getPreparedStatement(sql);

        if (ps == null)
            return;

        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Get the contact name from the database
     *
     * @param id id of the contact
     * @return the contact name
     */
    public static String getContactName(int id) {
        String sql = "SELECT Contact_Name\n" +
                "\tFROM contacts\n" +
                "    WHERE Contact_ID = " + id;
        ResultSet rs = getResultSet(sql);

        if (rs == null)
            return "";

        try {
            rs.next();
            return rs.getString("Contact_Name");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    /** Checks if a user exists in a database
     *
     * @param id id of the user
     * @return true if exists else false
     */
    public static boolean checkUserIdExists(int id) {
        String sql = "SELECT * FROM users WHERE User_ID = " + id;
        ResultSet rs = getResultSet(sql);

        if (rs == null)
            return false;

        try {
            if (!rs.next())
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    /** Deletes appointments given its id
     *
     * @param id id of the appointment
     */
    public static void deleteAppointmentWithAppointmentId(int id) {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = " + id;
        PreparedStatement ps = getPreparedStatement(sql);

        if (ps == null)
            return;

        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
