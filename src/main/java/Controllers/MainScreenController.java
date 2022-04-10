package Controllers;

import Models.Appointments;
import Models.Customer;
import Models.User;
import Utils.Alerts;
import Utils.ChangeScreen;
import Utils.SqlCommand;
import Utils.TimeZoneConvert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

/** The main scene of the program
 * @author Alex Bader
 * @version 1.0
 */

public class MainScreenController implements Initializable {

    /** The user that logged in */
    private static User user;

    /** table view of the customer */
    public TableView<Customer> customerTableView;

    /** id of the customer col */
    public TableColumn customerIdCol;

    /** name of the customer col */
    public TableColumn customerNameCol;

    /** address of the customer col */
    public TableColumn customerAddressCol;

    /** postal code of the customer col */
    public TableColumn customerPostalCodeCol;

    /** customer phone col */
    public TableColumn customerPhoneCol;

    /** customer div col */
    public TableColumn customerDivisionIdCol;

    /** appointments tableview */
    public TableView<Appointments> appointmentTableView;

    /** appointment id col */
    public TableColumn appointmentIdCol;

    /** appointment title col */
    public TableColumn appointmentTitleCol;

    /** appointment description col */
    public TableColumn appointmentDescriptionCol;

    /** appointment location col */
    public TableColumn appointmentLocationCol;

    /** appointment contact col */
    public TableColumn appointmentContactCol;

    /** appointment type col */
    public TableColumn appointmentTypeCol;

    /** appointment start col */
    public TableColumn appointmentStartCol;

    /** appointment end col */
    public TableColumn appointmentEndCol;

    /** appointment customer id col */
    public TableColumn appointmentCustomerIdCol;

    /** appointment user id col */
    public TableColumn appointmentUserIdCol;

    /** customer deleted label */
    public Label customerDeletedLabel;

    /** appointment deleted label */
    public Label appointmentDeletedLabel;

    /** no appointments label */
    public Label noAppointmentsLabel;

    /** appointments toggle */
    public ToggleGroup appointmentsToggleGroup;

    /** all appointments radio button */
    public RadioButton allAppointmentsRadioButton;

    /** monthly appointments radio button */
    public RadioButton monthlyAppointmentsRadioButton;

    /** weekly appointments radio button */
    public RadioButton weeklyAppointmentsRadioButton;

    /** boolean to true to execute function once */
    private static boolean onlyOnce = true;

    /**
     *
     * @param url to initalize
     * @param resourceBundle to initalize
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTableView.setItems(populateCustomerTable());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerDivisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        appointmentTableView.setItems(populateAppointmentsTable("SELECT * FROM appointments"));
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("startDateAndTime"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("endDateAndTime"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        appointmentsToggleGroup = new ToggleGroup();
        allAppointmentsRadioButton.setToggleGroup(appointmentsToggleGroup);
        monthlyAppointmentsRadioButton.setToggleGroup(appointmentsToggleGroup);
        weeklyAppointmentsRadioButton.setToggleGroup(appointmentsToggleGroup);

        if (onlyOnce) {
            onlyOnce = false;
            appointmentWithinFifteenMinutes();
        }

    }

    public static void setUser(User u) {
        user = u;
    }

    /**
     * Populate the table with customers
     * @return the list of customers from the table
     */
    private ObservableList<Customer> populateCustomerTable() {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers";
        ResultSet rs = SqlCommand.getResultSet(sql);

        if (rs == null)
            return null;

        try {
            while (rs.next()) {
                int custId = rs.getInt("Customer_ID");
                String custName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                int divId = rs.getInt("Division_ID");
                list.add(new Customer(custId, custName, address, postalCode, phoneNumber, divId));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Executes a sql statement and returns appointments
     * @param sql the statement to execute
     * @return the appointments from that execution
     */
    private ObservableList<Appointments> populateAppointmentsTable(String sql) {
        ObservableList<Appointments> list = FXCollections.observableArrayList();
        ResultSet rs = SqlCommand.getResultSet(sql);

        if (rs == null)
            return null;

        try {
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                int contactId = rs.getInt("Contact_ID");
                int userId = rs.getInt("User_ID");
                int customerId = rs.getInt("Customer_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                String startDate = TimeZoneConvert.utcTimeToLocalTime(rs.getString("Start"));
                String endDate = TimeZoneConvert.utcTimeToLocalTime(rs.getString("End"));
                Appointments appointments = new Appointments(appointmentId, title, description, location, contactId,
                        type, startDate, endDate, customerId, userId);
                list.add(appointments);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Change to customer add scene
     * @param event switch scenes
     * @throws IOException if fxml file not found
     */
    public void customerAddButtonPressed(ActionEvent event) throws IOException {
        ChangeScreen.change_screen(event, "AddCustomerController.fxml");
    }

    /** Change to customer update scene
     *
     * @param event switch scenes
     * @throws IOException if fxml file not found
     */
    public void customerUpdateButtonPressed(ActionEvent event) throws IOException {
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();

        if (customer == null)
            return;

        UpdateCustomerController.setCustomer(customer);
        ChangeScreen.change_screen(event, "UpdateCustomerController.fxml");
    }

    /**
     * Delete a customer and confirm
     */
    public void customerDeleteButtonPressed() {
        Customer c = customerTableView.getSelectionModel().getSelectedItem();

        if (c == null)
            return;

        boolean confirm = new Alerts("Confirm", "Confirm deletion", "Are you sure you want to delete " +
                "customer with id " + c.getId() + "?").confirmationDialog();

        if (!confirm)
            return;

        SqlCommand.deleteAppointmentsWithCustomer(c.getId());
        SqlCommand.deleteCustomerWithCustomerId(c.getId());
        customerTableView.setItems(populateCustomerTable());
        appointmentTableView.setItems(populateAppointmentsTable("SELECT * FROM appointments"));

        customerDeletedLabel.setText("Customer with ID " + c.getId() + " was deleted");
        customerDeletedLabel.setFont(Font.font(13));
        customerDeletedLabel.setTextFill(Paint.valueOf("#e60000"));
    }

    /** Switch to appointment add scene
     *
     * @param event to switch scenes
     * @throws IOException if fxml file not found
     */
    public void appointmentAddButtonPressed(ActionEvent event) throws IOException {
        AddAppointmentController.setUserId(user.getUserId());
        ChangeScreen.change_screen(event, "AddAppointmentController.fxml");
    }

    /** Switch to update appointment scene
     *
     * @param event to switch scenes
     * @throws IOException if fxml file not found
     */
    public void appointmentUpdateButtonPressed(ActionEvent event) throws IOException {
        Appointments appointment = appointmentTableView.getSelectionModel().getSelectedItem();

        if (appointment == null)
            return;

        UpdateAppointmentController.setAppointment(appointment);
        ChangeScreen.change_screen(event, "UpdateAppointmentController.fxml");
    }

    /**
     * Delete an appointment and confirm
     */
    public void appointmentDeleteButtonPressed() {
        Appointments appointment = appointmentTableView.getSelectionModel().getSelectedItem();

        if (appointment == null)
            return;

        boolean confirm = new Alerts("Confirm", "Confirm deletion", "Are you sure you want to delete " +
                "appointment with id " + appointment.getId() + "?").confirmationDialog();

        if (!confirm)
            return;

        SqlCommand.deleteAppointmentWithAppointmentId(appointment.getId());
        appointmentTableView.setItems(populateAppointmentsTable("SELECT * FROM appointments"));
        String str = "Appointment with ID " + appointment.getId() + " of type " + appointment.getType() + " was canceled";

        appointmentDeletedLabel.setText(str);
        appointmentDeletedLabel.setFont(Font.font(13));
        appointmentDeletedLabel.setTextFill(Paint.valueOf("#e60000"));
    }

    /**
     * Show all appointments
     */
    public void allAppointmentsRadioButtonClicked() {
        appointmentTableView.setItems(populateAppointmentsTable("SELECT * FROM appointments"));
    }

    /**
     * Show monthly appointments
     */
    public void monthlyAppointmentsRadioButtonClicked() {
        String sql = " SELECT *, SUBSTRING(Start, 1, 10) AS start_date, \n" +
                "\tCONCAT(YEAR(NOW()), \"-\", LPAD(MONTH(NOW()), 2,'0'), \"-\", \"01\") AS begin, \n" +
                "\tCONCAT(YEAR(NOW()), \"-\", LPAD(MONTH(NOW()), 2,'0'), \"-\", SUBSTRING(LAST_DAY(NOW()), 9, 11)) AS end\n" +
                "\tFROM appointments\n" +
                "    HAVING start_date BETWEEN begin AND end";
        appointmentTableView.setItems(populateAppointmentsTable(sql));
    }

    /**
     * Show weekly appointments
     */
    public void weeklyAppointmentsRadioButton() {
        String sql = "SELECT *, SUBDATE(CURRENT_DATE, DAYOFWEEK(CURRENT_DATE) - 1) AS first, \n" +
                "\tSUBDATE(CURRENT_DATE, DAYOFWEEK(CURRENT_DATE) - 7) AS second, \n" +
                "\tSUBSTRING(start, 1, 10) AS start_date\n" +
                "\tFROM appointments\n" +
                "    HAVING start_date BETWEEN first AND second";
        appointmentTableView.setItems(populateAppointmentsTable(sql));
    }

    /** Switch scenes to update date time
     *
     * @param event to switch scenes
     * @throws IOException if fxml file not found
     */
    public void updateDateTimeButtonPressed(ActionEvent event) throws IOException {
        Appointments p = appointmentTableView.getSelectionModel().getSelectedItem();

        if (p == null)
            return;

        UpdateAppointmentDateTime.setAppointment(p);
        ChangeScreen.change_screen(event, "UpdateAppointmentDateTime.fxml");
    }

    /**
     * Checks to see if there's any appointments within 15 minutes
     */
    private void appointmentWithinFifteenMinutes() {
        String sql = "SELECT * FROM appointments WHERE User_ID = " + user.getUserId();
        ResultSet rs = SqlCommand.getResultSet(sql);
        boolean flag = true;

        if (rs == null)
            return;

        try {
            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String start = TimeZoneConvert.utcTimeToLocalTime(rs.getString("Start"));
                String startDate = start.split("T")[0];
                String startTime = start.split("T")[1] + ":00";

                Instant instant = Instant.now();
                LocalDateTime ldt = instant.atZone(ZoneId.systemDefault()).toLocalDateTime().truncatedTo(ChronoUnit.SECONDS);
                String userDate = ldt.toString().split("T")[0];
                String userTime = ldt.toString().split("T")[1];

                if (startDate.equals(userDate)) {
                    LocalTime user = LocalTime.parse(userTime);
                    LocalTime db = LocalTime.parse(startTime);

                    if (db.getHour() >= user.getHour() && db.getMinute() >= user.getMinute()) {
                        long seconds = Duration.between(db, user).getSeconds() * -1;

                        if (seconds <= 900) {
                            flag = false;
                            noAppointmentsLabel.setText("You have an appointment within 15 minutes. " +
                                    "Appointment ID: " + id + " Date: " + userDate + " Time: " + startTime);
                            noAppointmentsLabel.setFont(Font.font(15));
                            noAppointmentsLabel.setTextFill(Paint.valueOf("#0066ff"));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (flag) {
            noAppointmentsLabel.setText("No upcoming appointments");
            noAppointmentsLabel.setFont(Font.font(18));
            noAppointmentsLabel.setTextFill(Paint.valueOf("#ff0000"));
        }
    }

    /** Switch scenes to reports
     *
     * @param event to switch scenes
     * @throws IOException if fxml file not found
     */
    public void reportsButtonPressed(ActionEvent event) throws IOException {
        ChangeScreen.change_screen(event, "ReportsController.fxml");
    }

    /** Goes back to log in screen
     *
     * @param event to switch scenes
     * @throws IOException if fxml file not found
     */
    public void logoutButtonPressed(ActionEvent event) throws IOException {
        ChangeScreen.change_screen(event, "login-page.fxml");
    }

    /**
     *
     * @param once set once
     */
    public static void setOnlyOnce(boolean once) {
        onlyOnce = once;
    }

}
