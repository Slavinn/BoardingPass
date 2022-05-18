package com.boardingpass.boardingpass;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
//        scene.getStylesheets().add("../../resources/css/control.css");
//        scene.getStylesheets().add("src/main/java/com/boardingpass/boardingpass/control.css").toExternalForm();
//        scene.getStylesheets().add(getClass().getResource("control.css").toExternalForm());
        stage.setTitle("Boarding Pass");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}