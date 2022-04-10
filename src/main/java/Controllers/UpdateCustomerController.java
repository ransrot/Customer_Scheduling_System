package Controllers;

import Models.Customer;
import Utils.Alerts;
import Utils.ChangeScreen;
import Utils.SqlCommand;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** Updates the customer
 * @author Alex Bader
 * @version 1.0
 */

public class UpdateCustomerController extends AddCustomerController {

    /** The customer to update */
    private static Customer customer;

    /** customer id text */
    public TextField customerId;

    /** Updates all texts accordingly and combo boxes
     *
     * @param url to initialize
     * @param resourceBundle to initialize
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerId.setText(String.valueOf(customer.getId()));
        customerId.disableProperty().set(true);
        nameTextField.setText(customer.getName());
        phoneTextField.setText(customer.getPhoneNumber());
        postalCodeTextField.setText(customer.getPostalCode());
        addressTextField.setText(customer.getAddress());


        setCountryComboBox();
        String country = SqlCommand.getCountry(customer.getDivisionId());
        countryComboBox.getSelectionModel().select(country);
        setStateComboBox(SqlCommand.getCountryId(country));
        stateComboBox.getSelectionModel().select(SqlCommand.getState(customer.getDivisionId()));
    }

    /**
     *
     * @param c set customer
     */
    public static void setCustomer(Customer c) {
        customer = c;
    }

    /** Updates customer and goes back to main screen
     *
     * @param event to change scenes
     * @throws IOException if fxml file not found
     */
    public void saveButtonPressedUpdate(ActionEvent event) throws IOException {
        if (!verifyInputs()) {
            new Alerts("Error", "Not all filled", "Everything needs to be filled in").errorDialog();
            return;
        }

        String name = nameTextField.getText();
        String phone = phoneTextField.getText();
        String postalCode = postalCodeTextField.getText();
        String address = addressTextField.getText();
        String state = stateComboBox.getSelectionModel().getSelectedItem();
        int divisionId = SqlCommand.getDivisionId(state);
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = NOW()" +
                ", Division_ID = ? WHERE Customer_ID = " + customer.getId();

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
            ps.setInt(5, divisionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ChangeScreen.change_screen(event, "MainScreenController.fxml");
    }


}
