package Main;

import Controllers.MainScreenController;
import Models.User;
import Utils.Alerts;
import Utils.ChangeScreen;
import Utils.SqlCommand;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/** Logic of the login page
 * @author Alex Bader
 * @version 1.0
 */

public class LoginController implements Initializable {

    /** username text */
    public TextField usernameText;

    /** password text */
    public TextField passwordText;

    /** login button */
    public Button loginButton;

    /** exit button */
    public Button exitButton;

    /** id of the zone */
    public Label zoneIdLabel;

    /** user name label */
    public Label userNameLabel;

    /** password label */
    public Label passwordLabel;

    /** login page label */
    public Label loginPageLabel;

    /** for languages */
    private ResourceBundle rb;

    /**
     *
     * @param url to initialize
     * @param resourceBundle to initialize
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MainScreenController.setOnlyOnce(true);
        rb = ResourceBundle.getBundle("Login", Locale.getDefault());
        setLocaleLanguage();
        setZoneId();
    }

    /** Sets texts according to the language
     *
     */
    private void setLocaleLanguage() {
        loginPageLabel.setText(rb.getString("loginPageLabel"));
        loginButton.setText(rb.getString("loginButton"));
        exitButton.setText(rb.getString("exitButton"));
        userNameLabel.setText(rb.getString("userNameLabel"));
        passwordLabel.setText(rb.getString("passwordLabel"));
    }

    /** Sets the zone id
     *
     */
    public void setZoneId() {
        zoneIdLabel.setText("ZoneID: " + ZoneId.systemDefault());
        zoneIdLabel.setFont(Font.font(13));
        zoneIdLabel.setTextFill(Paint.valueOf("#7300e6"));
    }

    /** Switches scenes and verify inputs
     *
     * @param event to switch scenes
     * @throws IOException if fxml file not found
     */
    public void loginButtonPressed(ActionEvent event) throws IOException {
        String userName = usernameText.getText();
        String password = passwordText.getText();

        if (userName.length() == 0 || password.length() == 0) {
            new Alerts(rb.getString("errorTitle"), rb.getString("errorHeader"), rb.getString("textFieldEmpty"))
                    .errorDialog();
            return;
        }

        boolean found = userNamePasswordFound(userName, password);
        if (!found) {
            new Alerts(rb.getString("errorTitle"), rb.getString("errorHeader"), rb.getString("contentError"))
                    .errorDialog();
            return;
        }

        MainScreenController.setUser(new User(userName, getUserId(userName, password)));
        ChangeScreen.change_screen(event, "MainScreenController.fxml");
    }

    /** Checks if a username/password exists in the data base
     *
     * @param userName username you input
     * @param password password you input
     * @return true if found in db else false
     */
    private boolean userNamePasswordFound(String userName, String password) {
        String sql = "SELECT * FROM users WHERE User_Name = '" + userName + "' AND Password = '" + password + "'";
        ResultSet rs = SqlCommand.getResultSet(sql);

        try {
            if (rs == null || !rs.next()) {
                logInformation(userName, false);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        logInformation(userName,true);
        return true;
    }

    /**
     *
     * @param userName the user name of the user
     * @param attempt whether the attempt was sucessful
     */
    private void logInformation(String userName, boolean attempt) {
        String dateAndTimeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        String userLogIn = attempt ? userName + " Logged in at " : "FAILED Log in attempt by " + userName + " at ";
        userLogIn += dateAndTimeStamp;

        try {
            FileWriter fWriter = new FileWriter("login_activity.txt", true);
            BufferedWriter buff = new BufferedWriter(fWriter);
            buff.append(userLogIn).append("\n");
            buff.close();
            fWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Exit the program
     */
    public void exitButtonPressed() {
        Platform.exit();
    }

    /**
     *
     * @param userName user name inputted
     * @param password password inputted
     * @return id of the user from the database
     */
    public int getUserId(String userName, String password) {
        String sql = "SELECT User_ID " +
                "FROM users WHERE User_Name = '" + userName + "' AND Password = '" + password + "'";
        ResultSet rs = SqlCommand.getResultSet(sql);

        try {
            rs.next();
            return rs.getInt("User_ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
