package Utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** This class changes screens to a different Controller
 * @author Alex Bader
 * @version 1.0
 */

public abstract class ChangeScreen {

    /** Can change screens to a different FXML file
     *
     * @param event the event to handle
     * @param file_name the name of the file to switch to
     * @throws IOException if failed to load FXML file
     */
    public static void change_screen(ActionEvent event, String file_name) throws IOException {
        Parent tableViewParent = FXMLLoader.load(Main.Main.class.getResource(file_name));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
