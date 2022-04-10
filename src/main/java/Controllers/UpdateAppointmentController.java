package Controllers;

import Models.Appointments;
import Utils.Alerts;
import Utils.ChangeScreen;
import Utils.SqlCommand;
import Utils.TimeZoneConvert;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/** Updates an appointment
 * @author Alex Bader
 * @version 1.0
 */

public class UpdateAppointmentController extends AddAppointmentController {

    /** The appointment to update */
    protected static Appointments appointment;

    /** Sets all the fields accordingly
     *
     * @param url to initialize
     * @param resourceBundle to initialize
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentIdText.disableProperty().set(true);
        appointmentIdText.setText(String.valueOf(appointment.getId()));
        userIdText.setText(String.valueOf(appointment.getUserId()));
        customerIdText.setText(String.valueOf(appointment.getCustomerId()));
        descriptionText.setText(appointment.getDescription());
        titleText.setText(appointment.getTitle());
        typeText.setText(appointment.getType());
        locationText.setText(appointment.getLocation());
        setDatePickerFieldUpdate();

        setTimeComboBoxes();
        setContactComboBox();
        setComboBoxHoursAndMinutes();
        contactComboBox.getSelectionModel().select(SqlCommand.getContactName(appointment.getContactId()));
    }

    /**
     *
     * @param p set appointment
     */
    public static void setAppointment(Appointments p) {
        appointment = p;
    }

    /**
     * Sets the correct date value
     */
    protected void setDatePickerFieldUpdate() {
        datePickerField.getEditor().setDisable(true);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(appointment.getStartDateAndTime().split("T")[0], formatter);
        datePickerField.setValue(localDate);
    }

    /**
     * Sets the correct combo box fields
     */
    protected void setComboBoxHoursAndMinutes() {
        String start = appointment.getStartDateAndTime().split("T")[1];
        String end = appointment.getEndDateAndTime().split("T")[1];

        startHourComboBox.getSelectionModel().select(start.split(":")[0]);
        startMinuteComboBox.getSelectionModel().select(start.split(":")[1]);

        endHourComboBox.getSelectionModel().select(end.split(":")[0]);
        endMinuteComboBox.getSelectionModel().select(end.split(":")[1]);
    }

    /** Updates the changes and goes back to main screen
     *
     * @param event to switch scenes
     * @throws IOException if fxml file not found
     */
    public void saveButtonPressedUpdate(ActionEvent event) throws IOException {

        if (!verifyInputs())
            return;

        if (!isInteger(userIdText.getText())) {
            new Alerts("Error", "User ID", "User ID has to be an integer").errorDialog();
            return;
        }

        if (!SqlCommand.checkUserIdExists(Integer.parseInt(userIdText.getText()))) {
            new Alerts("Error", "User ID", "User ID does not exist.").errorDialog();
            return;
        }

        String userId = userIdText.getText();
        String customerId = customerIdText.getText();
        String description = descriptionText.getText();
        String title = titleText.getText();
        String type = typeText.getText();
        String location = locationText.getText();
        String date = datePickerField.getValue().toString();
        String startHour = startHourComboBox.getSelectionModel().getSelectedItem();
        String startMin = startMinuteComboBox.getSelectionModel().getSelectedItem();
        String endHour = endHourComboBox.getSelectionModel().getSelectedItem();
        String endMinute = endMinuteComboBox.getSelectionModel().getSelectedItem();
        String contact = contactComboBox.getSelectionModel().getSelectedItem();

        String timeFull = date + " " + startHour + ":" + startMin + ":00";
        String timeEnd = date + " " + endHour + ":" + endMinute + ":00";

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


        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, " +
                "Start = ?, End = ?, Create_Date = NOW(), Created_By = ?, Last_Update = NOW(), " +
                "Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                "WHERE Appointment_ID = ?";

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
            ps.setInt(10, Integer.parseInt(userId));
            ps.setInt(11, SqlCommand.getContactId(contact));
            ps.setInt(12, appointment.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ChangeScreen.change_screen(event, "MainScreenController.fxml");
    }


}
