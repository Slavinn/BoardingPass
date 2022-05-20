package com.boardingpass.boardingpass;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class PopUp {


    public static void display() {
        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Purchase information");


        Label label1 = new Label("Your BoardingPass has been generated to your local file directory");


        Button button1 = new Button("Close application");


        button1.setOnAction(e -> popupwindow.close());


        VBox layout = new VBox(10);


        layout.getChildren().addAll(label1, button1);

        layout.setAlignment(Pos.CENTER);

        Scene scene1 = new Scene(layout, 400, 300);

        popupwindow.setScene(scene1);

        popupwindow.showAndWait();

    }

}

