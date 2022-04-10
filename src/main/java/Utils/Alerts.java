package Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/** Responsible for making dialog boxes for the program
 * @author Alex Bader
 * @version 1.0
 */
public class Alerts {
    /** The title to put in the dialog box */
    private String title;

    /** The header to put in the dialog box */
    private String headerText;

    /** The content to display in the dialog box */
    private String contentText;

    /** Construct a class to make dialog boxes
     *
     * @param title the title to put in the dialog box
     * @param headerText the header to put in the dialog box
     * @param contentText the content to display in the dialog box
     */
    public Alerts(String title, String headerText, String contentText) {
        this.title = title;
        this.headerText = headerText;
        this.contentText = contentText;
    }

    /** Construct a class with no parameters to make dialog boxes
     *
     */
    public Alerts() {
        this.title = null;
        this.headerText = null;
        this.contentText = null;
    }

    /** Sets the title
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Sets the header
     *
     * @param headerText the headerText to set
     */
    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    /** Sets the contentText
     *
     * @param contentText the contentText to set
     */
    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    /** Gives an error dialog box
     *
     */
    public void errorDialog() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /** Gives a confirmation dialog box
     *
     * @return true if the user clicks confirm else false
     */
    public boolean confirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(null) == ButtonType.OK;
    }
}