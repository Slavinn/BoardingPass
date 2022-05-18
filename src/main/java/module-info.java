module com.boardingpass.boardingpass {
    requires javafx.controls;
    requires javafx.fxml;
    requires async.http.client;
    requires json.simple;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;


    opens com.boardingpass.boardingpass to javafx.fxml;
    exports com.boardingpass.boardingpass;
    exports com.boardingpass.boardingpass.datamodel;
}