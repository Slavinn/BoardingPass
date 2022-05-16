package com.boardingpass.boardingpass;

import com.boardingpass.boardingpass.datamodel.BoardingPass;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.security.NoSuchAlgorithmException;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Controller {


    public BoardingPass boardingPassData;
    @FXML
    public TextField passengerPhoneNumber;
    @FXML
    public TextField passengerEmail;
    @FXML
    public DatePicker passengerDepartureDate;
    @FXML
    public TextField passengerName;




    public void initialize() {
        boardingPassData = new BoardingPass();

    }

    public void handleSubmitButtonAction() throws NoSuchAlgorithmException {
        boardingPassData.setDepartureDate(passengerDepartureDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        boardingPassData.setName(passengerName.toString());
        boardingPassData.setEmail(passengerEmail.toString());


        // needs to be ran after name and depatureDate have been set
        boardingPassData.generateBoardingPassNumber();

    }

}