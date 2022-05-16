module com.boardingpass.boardingpass {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.boardingpass.boardingpass to javafx.fxml;
    exports com.boardingpass.boardingpass;
}