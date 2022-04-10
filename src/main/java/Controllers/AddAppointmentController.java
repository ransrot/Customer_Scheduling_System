package Controllers;

import Utils.Alerts;
import Utils.ChangeScreen;
import Utils.SqlCommand;
import Utils.TimeZoneConvert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Adds an appointment
 * @author Alex Bader
 * @version 1.0
 */

public class AddAppointmentController implements Initializable {

    /** The id of the user */
    private static int userId;

    /** appointment text */
    public TextField appointmentIdText;

    /** user if text */
    public TextField userIdText;

    /** customer id text */
    public TextField customerIdText;

    /** description text */
    public TextField descriptionText;

    /** title text */
    public TextField titleText;

    /** type text */
    public TextField typeText;

    /** location text */
    public TextField locationText;

    /** selects the dates */
    public DatePicker datePickerField;

    /** start hour of combo box */
    public ComboBox<String> startHourComboBox;

    /** start minute of combo box */
    public ComboBox<String> startMinuteComboBox;

    /** end hour of combo box */
    public ComboBox<String> endHourComboBox;

    /** end minute of combo box */
    public ComboBox<String> endMinuteComboBox;

    /** contact combo box */
    public ComboBox<String> contactComboBox;

    /**
     *
     * @param url to initialize
     * @param resourceBundle to initialize
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentIdText.disableProperty().set(true);
        int appointmentId = SqlCommand.getNextAutoIncrementTable("appointments");
        appointmentIdText.setText(String.valueOf(appointmentId));
        userIdText.disableProperty().set(true);
        userIdText.setText(String.valueOf(userId));
        datePickerField.getEditor().setDisable(true);
        setTimeComboBoxes();
        setContactComboBox();
    }

    /**
     *
     * @param id set id
     */
    public static void setUserId(int id) {
        userId = id;
    }

    /**
     *
     * @param event to switch scenes
     * @throws IOException if fxml file doesn't exist
     */
    public void cancelButtonPressed(ActionEvent event) throws IOException {
        ChangeScreen.change_screen(event, "MainScreenController.fxml");
    }

    /**
     * <p>
     *     FIRST LAMBDA IN HERE
     *     I used lambdas for this function since I did not want to use a for loop to make an array. A for loop takes
     *     more space if you don't use a lambda.
     *     Sets the time combo boxes
     * </p>
     */
    public void setTimeComboBoxes() {
        ObservableList<String> hoursList = FXCollections.observableArrayList();
        ObservableList<String> minutesList = FXCollections.observableArrayList();

        List<String> list = Stream.iterate(0, n -> n + 1).limit(10).map(i -> "0" + i).collect(Collectors.toList());
        List<String> list2 = Stream.iterate(10, n -> n + 1).limit(14).map(String::valueOf).collect(Collectors.toList());
        List<String> list3 = Stream.iterate(10, n -> n + 1).limit(50).map(String::valueOf).collect(Collectors.toList());

        hoursList.addAll(list);
        hoursList.addAll(list2);
        minutesList.addAll(list);
        minutesList.addAll(list3);

        startHourComboBox.setItems(hoursList);
        startMinuteComboBox.setItems(minutesList);
        endHourComboBox.setItems(hoursList);
        endMinuteComboBox.setItems(minutesList);
    }

    /**
     * Sets the contact combo box
     */
    public void setContactComboBox() {
        String sql = "SELECT * FROM contacts";
        ResultSet rs = SqlCommand.getResultSet(sql);
        ObservableList<String> list = FXCollections.observableArrayList();

        if (rs == null)
            return;

        try {
            while (rs.next()) {
                list.add(rs.getString("Contact_Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        contactComboBox.setItems(list);
        contactComboBox.getSelectionModel().selectFirst();
    }

    /** Adds the appointment and go to main screen
     *
     * @param event to switch scenes
     * @throws IOException if fxml file not found
     */
    public void saveButtonPressed(ActionEvent event) throws IOException {
        if (!verifyInputs())
            return;

        String customerId = customerIdText.getText();
        String description = descriptionText.getText();
        String title = titleText.getText();
        String type = typeText.getText();
        String location = locationText.getText();
        String date = datePickerField.getValue().toString();
        String startHour = startHourComboBox.getSelectionModel().getSelectedItem();
        String endHour = endHourComboBox.getSelectionModel().getSelectedItem();
        String startMin = startMinuteComboBox.getSelectionModel().getSelectedItem();
        String endMin = endMinuteComboBox.getSelectionModel().getSelectedItem();
        String contactName = contactComboBox.getSelectionModel().getSelectedItem();

        String timeFull = date + " " + startHour + ":" + startMin + ":00";
        String timeEnd = date + " " + endHour + ":" + endMin + ":00";


        if (!verifyBusinessHours(timeFull, timeEnd)) {
            new Alerts("Error", "Outside business hours", "The time you input is outside " +
                    "business hours. Time for business hours is 8am to 10pm EST").errorDialog();
            return;
        }

        if (checkAppointmentOverlap(Integer.parseInt(customerId), timeFull, timeEnd)) {
            new Alerts("Error", "Appointment overlap", "Appointment overlap with one of the " +
                    "appointments you made").errorDialog();
            return;
        }

        String utcStart = TimeZoneConvert.localTimeToUtc(timeFull);
        String utcEnd = TimeZoneConvert.localTimeToUtc(timeEnd);

        String sql = "INSERT INTO appointments VALUES(DEFAULT,?,?,?,?,?,?,NOW(),?,NOW(),?,?,?,?)";
        PreparedStatement ps = SqlCommand.getPreparedStatement(sql);

        if (ps == null)
            return;

        try {
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setString(5, utcStart);
            ps.setString(6, utcEnd);
            ps.setString(7, "script");
            ps.setString(8, "script");
            ps.setInt(9, Integer.parseInt(customerId));
            ps.setInt(10, userId);
            ps.setInt(11, SqlCommand.getContactId(contactName));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ChangeScreen.change_screen(event, "MainScreenController.fxml");
    }

    /** Verify the inputs
     *
     * @return true if inputs verified else false
     */
    protected boolean verifyInputs() {
        Alerts alerts = new Alerts("Error", "Filled", "Everything needs to have a value");

        if (datePickerField.getValue() == null) {
            alerts.errorDialog();
            return false;
        }

        String customerId = customerIdText.getText();
        String description = descriptionText.getText();
        String title = titleText.getText();
        String type = typeText.getText();
        String location = locationText.getText();
        String startHour = startHourComboBox.getSelectionModel().getSelectedItem();
        String endHour = endHourComboBox.getSelectionModel().getSelectedItem();
        String startMin = startMinuteComboBox.getSelectionModel().getSelectedItem();
        String endMin = endMinuteComboBox.getSelectionModel().getSelectedItem();
        String contact = contactComboBox.getSelectionModel().getSelectedItem();

        if (customerId.length() == 0 || description.length() == 0 || title.length() == 0 || type.length() == 0 ||
        location.length() == 0 || startHour == null || endHour == null || startMin == null || endMin == null || contact == null) {
            alerts.errorDialog();
            return false;
        }

        if (!isInteger(customerId) || !SqlCommand.checkCustomerExists(Integer.parseInt(customerId))) {
            alerts.setHeaderText("Customer ID invalid");
            alerts.setContentText("Customer ID value is invalid. Customer doesn't exist or not an integer.");
            alerts.errorDialog();
            return false;
        }

        LocalTime lt1 = LocalTime.parse(startHour + ":" + startMin + ":00");
        LocalTime lt2 = LocalTime.parse(endHour + ":" + endMin + ":00");

        if (Duration.between(lt2, lt1).getSeconds() > 0) {
            new Alerts("Error", "Duration", "The end time has to be greater or equal to start time.")
                    .errorDialog();
            return false;
        }

        return true;
    }

    /** Checks if a string could be an integer
     *
     * @param str the str to check
     * @return true if its an integer else false
     */
    protected boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    /** Checks if the users appointments falls within the business hours
     *
     * @param startTimeLocal the start local time
     * @param endTimeLocal the end local time
     * @return true if its in the range of business hours else false
     */
    protected boolean verifyBusinessHours(String startTimeLocal, String endTimeLocal) {
        String[] convertStart = TimeZoneConvert.localTimeToEst(startTimeLocal).split(" ")[1].split(":");
        String[] convertEnd = TimeZoneConvert.localTimeToEst(endTimeLocal).split(" ")[1].split(":");
        int cov1 = Integer.parseInt(convertStart[0] + convertStart[1]);
        int cov2 = Integer.parseInt(convertEnd[0] + convertEnd[1]);
        int estStart = 800;
        int estEnd = 2200;

        return (cov1 >= estStart && cov2 >= estStart) && (cov1 <= estEnd && cov2 <= estEnd);
    }

    /** Checks to see if there's an overlap of appointments for the customer
     *
     * @param customerId id of customer
     * @param startTimeUser start time of user
     * @param endTimeUser end time of user
     * @return true if appointment overlap else false
     */
    protected boolean checkAppointmentOverlap(int customerId, String startTimeUser, String endTimeUser) {
        String userDate = startTimeUser.split(" ")[0];
        String[] splitStartTimeUser = startTimeUser.split(" ")[1].split(":");
        String[] splitEndTimeUser = endTimeUser.split(" ")[1].split(":");

        int startUser = Integer.parseInt(splitStartTimeUser[0] + splitStartTimeUser[1]);
        int endUser = Integer.parseInt(splitEndTimeUser[0] + splitEndTimeUser[1]);


        String sql = "SELECT * FROM appointments WHERE Customer_ID = " + customerId + " AND Appointment_ID != " +
                Integer.parseInt(appointmentIdText.getText());
        ResultSet rs = SqlCommand.getResultSet(sql);

        if (rs == null)
            return false;

        try {
            while (rs.next()) {
                String startDb = TimeZoneConvert.utcTimeToLocalTime(rs.getString("Start"));
                String endDb = TimeZoneConvert.utcTimeToLocalTime(rs.getString("End"));


                String dbDate = startDb.split("T")[0];
                String[] splitStartDb = startDb.split("T")[1].split(":");
                String[] splitEndDb = endDb.split("T")[1].split(":");

                int startDbTime = Integer.parseInt(splitStartDb[0] + splitStartDb[1]);
                int endDbTime = Integer.parseInt(splitEndDb[0] + splitEndDb[1]);

                if (userDate.equals(dbDate)) {
                    if (startUser >= startDbTime && startUser <= endDbTime)
                        return true;

                    if (startUser <= startDbTime && endUser >= startDbTime)
                        return true;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


}
