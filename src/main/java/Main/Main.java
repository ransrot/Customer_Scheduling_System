package Main;

import Database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/** Represents the start of the application
 * @author Alex Bader
 * @version 1.0
 */

public class Main extends Application {

    /** Creates the UI
     *
     * @param stage for the ui
     * @throws IOException if cant find file
     */

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 599, 489);
        stage.setTitle("C195");
        stage.setScene(scene);
        stage.show();
    }

    /** Starts the program. Launches the UI and connects to the DataBase
     *
     * @param args for command line
     */
    public static void main(String[] args) {
        DBConnection.openConnection();
        launch();
        DBConnection.closeConnection();
    }
}
