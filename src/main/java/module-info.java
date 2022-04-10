module com.example.software2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens Main to javafx.fxml;
    opens Controllers to javafx.fxml;
    opens Models to javafx.fxml;

    exports Main;
    exports Controllers;
    exports Models;
}