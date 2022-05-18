package com.boardingpass.boardingpass;

import com.boardingpass.boardingpass.datamodel.Airport;
import com.boardingpass.boardingpass.datamodel.Airports;
import com.boardingpass.boardingpass.datamodel.BoardingPass;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class Controller {

    private AnimationTimer appLoop;
    private ArrayList<String> airportNames;
    public Label outputFlightTime;
    public Label outputArrivalTime;
    public Label outputFlightDuration;
    public Label outputTicketPrice;
    public Label outputLadiesDiscountAmount;
    public Label outputAgeDiscountAmount;
    public Label outputTotalCost;
    public Button bookFlight_btn;
    public Label outputEstimate;
    private BoardingPass boardingPassData;

    @FXML
    private TextField inputName;
    @FXML
    private TextField inputAge;
    @FXML
    private ChoiceBox<String> inputGenderSelection;
    @FXML
    private TextField inputPhoneNumber;
    @FXML
    private TextField inputEmail;
    @FXML
    private DatePicker inputDate;
    @FXML
    private AutoCompleteTextField inputOrigin;
    @FXML
    private AutoCompleteTextField inputDestination;
    @FXML
    private ChoiceBox<String> inputDepartTime;
//    @FXML
//    private String outputFlightTime;


    public void initialize() {
        boardingPassData = new BoardingPass();
        inputGenderSelection.getItems().addAll("Male", "Female");
        inputDepartTime.getItems().addAll("6:00 AM","9:30 AM","1:00 PM","4:45 PM","6:00 PM","10:00 PM");
        ObservableList<String> data = FXCollections.observableArrayList();
        airportNames = new ArrayList<>();
//        inputOrigin.setVisible(false);
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Airports.getInstance().fetchAirports();

                    for (Airport airport : Airports.getInstance().getAirportList()) {
                        airportNames.add(airport.getName());

                    }
                    inputOrigin.getEntries().addAll(airportNames);
                    inputDestination.getEntries().addAll(airportNames);
                    System.out.println("Wait for me to print before typing in text box: Add a hide and show visibility");

                } catch (ExecutionException | InterruptedException | ParseException | JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        new Thread(task).start();

    }

    public void onBookFlightButtonPress() {
        // write data to file using boardingPassData.toString()
    }

    public void onEstimateButtonPress() {
        boardingPassData.setName(inputName.getText());
        boardingPassData.setAge(Integer.parseInt(inputAge.getText()));
        boardingPassData.setGender(inputGenderSelection.getValue().toLowerCase(Locale.ROOT));

        // validate phoneNumber
        boardingPassData.setPhoneNumber(inputPhoneNumber.getText());
        boardingPassData.setEmail(inputEmail.getText());
        System.out.println(boardingPassData.toString());
    }


}