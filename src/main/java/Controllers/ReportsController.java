package Controllers;

import Models.Contact;
import Models.Month;
import Models.Type;
import Utils.ChangeScreen;
import Utils.SqlCommand;
import Utils.TimeZoneConvert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** Reports view
 * @author Alex Bader
 * @version 1.0
 */

public class ReportsController implements Initializable {

    /** Type tableview */
    public TableView<Type> typeTableView;

    /** type col */
    public TableColumn typeTypeCol;

    /** number of types */
    public TableColumn typeCountCol;

    /** month tableview */
    public TableView<Month> monthTableView;

    /** january col */
    public TableColumn janCol;

    /** february col */
    public TableColumn febCol;

    /** march col */
    public TableColumn marchCol;

    /** april col */
    public TableColumn aprilCol;

    /** may col */
    public TableColumn mayCol;

    /** june col */
    public TableColumn juneCol;

    /** july col */
    public TableColumn julyCol;

    /** august col */
    public TableColumn augCol;

    /** september col */
    public TableColumn sepCol;

    /** october col */
    public TableColumn octCol;

    /** november col */
    public TableColumn novCol;

    /** december col */
    public TableColumn decCol;

    /** contact table view */
    public TableView<Contact> contactTableView;

    /** contact appointment col */
    public TableColumn contactAppointmentCol;

    /** contact title col */
    public TableColumn contactTitleCol;

    /** contact type col */
    public TableColumn contactTypeCol;

    /** contact description col */
    public TableColumn contactDescriptionCol;

    /** contact start col */
    public TableColumn contactStartCol;

    /** contact end col */
    public TableColumn contactEndCol;

    /** contact customer id col */
    public TableColumn contactCustomerIdCol;

    /** contact combo box */
    public ComboBox<String> contactComboBox;

    /** customers count text */
    public TextField customersCountTextField;

    /**
     *
     * @param url to initialize
     * @param resourceBundle to initialize
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeTableView.setItems(setTypeTableView());
        typeTypeCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeCountCol.setCellValueFactory(new PropertyValueFactory<>("count"));

        monthTableView.setItems(setMonthTableView());
        janCol.setCellValueFactory(new PropertyValueFactory<>("janCount"));
        febCol.setCellValueFactory(new PropertyValueFactory<>("febCount"));
        marchCol.setCellValueFactory(new PropertyValueFactory<>("marchCount"));
        aprilCol.setCellValueFactory(new PropertyValueFactory<>("aprilCount"));
        mayCol.setCellValueFactory(new PropertyValueFactory<>("mayCount"));
        juneCol.setCellValueFactory(new PropertyValueFactory<>("juneCount"));
        julyCol.setCellValueFactory(new PropertyValueFactory<>("julyCount"));
        augCol.setCellValueFactory(new PropertyValueFactory<>("augCount"));
        sepCol.setCellValueFactory(new PropertyValueFactory<>("sepCount"));
        octCol.setCellValueFactory(new PropertyValueFactory<>("octCount"));
        novCol.setCellValueFactory(new PropertyValueFactory<>("novCount"));
        decCol.setCellValueFactory(new PropertyValueFactory<>("decCount"));

        populateContactComboBox();
        contactComboBoxSelected();
        contactAppointmentCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        contactTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        contactDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        contactStartCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        contactEndCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        contactCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        contactTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        customersCountTextField.disableProperty().set(true);
        customersCountTextField.setText(String.valueOf(countCustomers()));

    }

    /** Sets the type table view accordingly
     *
     * @return observableList for table view
     */
    private ObservableList<Type> setTypeTableView() {
        String sql = "SELECT *, COUNT(Type) AS type_count\n" +
                "\tFROM appointments\n" +
                "    GROUP BY Type";

        ObservableList<Type> list = FXCollections.observableArrayList();
        ResultSet rs = SqlCommand.getResultSet(sql);

        if (rs == null)
            return list;

        try {
            while (rs.next()) {
                list.add(new Type(rs.getString("Type"), rs.getInt("type_count")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /** Sets the month table view accordingly
     *
     * @return observableList for table view
     */
    private ObservableList<Month> setMonthTableView() {
        String sql = "SELECT *, MONTH(Start) AS month, COUNT(MONTH(Start)) AS count\n" +
                "\tFROM appointments\n" +
                "    GROUP BY MONTH(Start)";

        ObservableList<Month> list = FXCollections.observableArrayList();
        ResultSet rs = SqlCommand.getResultSet(sql);
        int[] m = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        if (rs == null)
            return list;

        try {
            while (rs.next()) {
                int monthNum = rs.getInt("month");
                int count = rs.getInt("count");
                m[monthNum - 1] = count;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        list.add(new Month(m[0], m[1], m[2], m[3], m[4], m[5], m[6], m[7], m[8], m[9], m[10], m[11]));
        return list;
    }

    /**
     * Populates the contact combo box
     */
    private void populateContactComboBox() {
        ObservableList<String> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Contacts";
        ResultSet rs = SqlCommand.getResultSet(sql);

        if (rs == null)
            return;

        try {
            while (rs.next()) {
                String name = rs.getString("Contact_Name");
                list.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        contactComboBox.setItems(list);
        contactComboBox.getSelectionModel().selectFirst();
    }

    /**
     * Sets the contact table view from sql statements
     */
    public void contactComboBoxSelected() {
        int contactId = SqlCommand.getContactId(contactComboBox.getSelectionModel().getSelectedItem());
        String sql = "SELECT *\n" +
                "\tFROM Contacts\n" +
                "    INNER JOIN appointments\n" +
                "    ON appointments.Contact_ID = Contacts.Contact_ID\n" +
                "    WHERE Contacts.Contact_ID = " + contactId;
        ResultSet rs = SqlCommand.getResultSet(sql);
        ObservableList<Contact> list = FXCollections.observableArrayList();

        if (rs == null)
            return;

        try {
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                int customerId = rs.getInt("Customer_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String description = rs.getString("Description");
                String startTime = TimeZoneConvert.utcTimeToLocalTime(rs.getString("Start"));
                String endTime = TimeZoneConvert.utcTimeToLocalTime(rs.getString("End"));
                list.add(new Contact(appointmentId, title, type, description, startTime, endTime, customerId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        contactTableView.setItems(list);
    }

    /** Exits the report back to main menu
     *
     * @param event to change scenes
     * @throws IOException if fxml file not found
     */
    public void exitButtonPressed(ActionEvent event) throws IOException {
        ChangeScreen.change_screen(event, "MainScreenController.fxml");
    }

    /** Counts the amount of customers in the database
     *
     * @return the amount of customers
     */
    private int countCustomers() {
        int count = 0;
        String sql = "SELECT * FROM Customers";
        ResultSet rs = SqlCommand.getResultSet(sql);

        if (rs == null)
            return 0;

        try {
            while (rs.next())
                count++;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

}
