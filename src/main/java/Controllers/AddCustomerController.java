package Controllers;

import Utils.Alerts;
import Utils.ChangeScreen;
import Utils.SqlCommand;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

/** Adds a customer to the table
 * @author Alex Bader
 * @version 1.0
 */

public class AddCustomerController implements Initializable {

    /** name text field */
    public TextField nameTextField;

    /** phone text field */
    public TextField phoneTextField;

    /** postal code text field */
    public TextField postalCodeTextField;

    /** address text field */
    public TextField addressTextField;

    /** country combo box */
    public ComboBox<String> countryComboBox;

    /** state combo box */
    public ComboBox<String> stateComboBox;

    /** save button */
    public Button saveButton;

    /** cancel button */
    public Button cancelButton;

    /**
     *
     * @param url to initialize
     * @param resourceBundle to initialize
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCountryComboBox();
    }

    /**
     *
     * <p>
     *    SECOND LAMBDA IN HERE
     *    I used a lambda expression in here to not have to use a for loop to loop through everything in
     *    the string array. Instead, it can be done in one line with a simple lambda expression.
     *    Sets the country combo box with values
     * </p>
     */
    public void setCountryComboBox() {
        ObservableList<String> list = FXCollections.observableArrayList();
        String[] countries = {"U.S", "UK", "Canada"};
        Arrays.asList(countries).forEach(c -> list.add(c));
        countryComboBox.setItems(list);
    }

    /**
     * Return to main screen
     * @param event switch scenes
     * @throws IOException if fxml file doesn't exist
     */
    public void cancelButtonPressed(ActionEvent event) throws IOException {
        ChangeScreen.change_screen(event, "MainScreenController.fxml");
    }

    /**
     * Sets the id of a country in the combo box
     */
    public void countryComboBoxSelected() {
        String country = countryComboBox.getSelectionModel().getSelectedItem();
        int id = SqlCommand.getCountryId(country);
        setStateComboBox(id);
    }

    /**
     * Sets the state combo box
     * @param id the id to set in the combo box
     */
    public void setStateComboBox(int id) {
        String sql = "SELECT DISTINCT Division\n" +
                "\tFROM countries\n" +
                "    INNER JOIN first_level_divisions\n" +
                "    ON " + id + "  = first_level_divisions.COUNTRY_ID";

        ResultSet rs = SqlCommand.getResultSet(sql);
        ObservableList<String> states = FXCollections.observableArrayList();

        if (rs == null)
            return;

        try {
            while (rs.next()) {
                states.add(rs.getString("Division"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        stateComboBox.setItems(states);
        stateComboBox.getSelectionModel().selectFirst();
    }

    /** Saves the information and adds it to the table
     *
     * @param event to switch scenes
     * @throws IOException if fxml file not found
     */
    public void saveButtonPressed(ActionEvent event) throws IOException {

        if (!verifyInputs()) {
            new Alerts("Error", "Not all filled", "Everything needs to be filled in").errorDialog();
            return;
        }

        String name = nameTextField.getText();
        String phone = phoneTextField.getText();
        String postalCode = postalCodeTextField.getText();
        String country = countryComboBox.getSelectionModel().getSelectedItem();
        String state = stateComboBox.getSelectionModel().getSelectedItem();
        String address = addressTextField.getText() + ", " + state + ", " + country;
        int divId = SqlCommand.getDivisionId(state);

        String sql = "INSERT INTO customers VALUES(DEFAULT, ?, ?, ?, ?, NOW(), ?, NOW(), ?,?)";
        PreparedStatement ps = SqlCommand.getPreparedStatement(sql);

        if (ps == null) {
            ChangeScreen.change_screen(event, "MainScreenController.fxml");
            return;
        }

        try {
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, "script");
            ps.setString(6, "script");
            ps.setInt(7, divId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ChangeScreen.change_screen(event, "MainScreenController.fxml");
    }

    /** Checks if all inputs have values in them
     *
     * @return true if the inputs are good else false
     */
    protected boolean verifyInputs() {
        return nameTextField.getText().length() > 0 && phoneTextField.getText().length() > 0 && postalCodeTextField
                .getText().length() > 0 && addressTextField.getText().length() > 0 && !countryComboBox.getSelectionModel()
                .isEmpty() && !stateComboBox.getSelectionModel().isEmpty();
    }

}
