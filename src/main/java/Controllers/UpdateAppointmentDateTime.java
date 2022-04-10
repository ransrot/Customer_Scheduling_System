package Controllers;

import Utils.Alerts;
import Utils.ChangeScreen;
import Utils.SqlCommand;
import Utils.TimeZoneConvert;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** Updates appointment date and/or time
 * @author Alex Bader
 * @version 1.0
 */

public class UpdateAppointmentDateTime extends UpdateAppointmentController {

    /** Sets all the fields accordingly
     *
     * @param url to initialize
     * @param resourceBundle to initialize
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDatePickerFieldUpdate();
        setTimeComboBoxes();
        setComboBoxHoursAndMinutes();
    }

    /** Updates the fields and returns to main screen
     *
     * @param event to initialize
     * @throws IOException if fxml not found
     */
    public void saveButtonUpdateAppointmentDateTimePressed(ActionEvent event) throws IOException {

        String date = datePickerField.getValue().toString();
        String startHour = startHourComboBox.getSelectionModel().getSelectedItem();
        String startMin = startMinuteComboBox.getSelectionModel().getSelectedItem();
        String endHour = endHourComboBox.getSelectionModel().getSelectedItem();
        String endMinute = endMinuteComboBox.getSelectionModel().getSelectedItem();

        String timeFull = date + " " + startHour + ":" + startMin + ":00";
        String timeEnd = date + " " + endHour + ":" + endMinute + ":00";

        if (!verifyBusinessHours(timeFull, timeEnd)) {
            new Alerts("Error", "Outside business hours", "The time you input is outside " +
                    "business hours. Time for business hours is 8am to 10pm EST").errorDialog();
            return;
        }

        if (checkAppointmentOverlap(appointment.getCustomerId(), timeFull, timeEnd)) {
            new Alerts("Error", "Appointment overlap", "Appointment overlap with one of the " +
                    "appointments you made").errorDialog();
            return;
        }

        String utcStart = TimeZoneConvert.localTimeToUtc(timeFull);
        String utcEnd = TimeZoneConvert.localTimeToUtc(timeEnd);

        String sql = "UPDATE appointments SET Start = ?, End = ? WHERE Appointment_ID = " + appointment.getId();
        PreparedStatement ps = SqlCommand.getPreparedStatement(sql);

        if (ps == null)
            return;

        try {
            ps.setString(1, utcStart);
            ps.setString(2, utcEnd);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ChangeScreen.change_screen(event, "MainScreenController.fxml");
    }


}
