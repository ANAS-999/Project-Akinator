module com.ensa.akinator {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.sql;

    opens com.ensa.akinator to javafx.fxml;
    exports com.ensa.akinator;

    opens com.ensa.akinator.controllers to javafx.fxml;
    exports com.ensa.akinator.controllers;
}
