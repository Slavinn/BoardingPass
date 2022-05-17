package com.boardingpass.boardingpass;

import com.boardingpass.boardingpass.datamodel.Airport;
import com.boardingpass.boardingpass.datamodel.Airports;
import com.boardingpass.boardingpass.datamodel.BoardingPass;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import np.com.ngopal.control.AutoFillTextBox;
import org.json.simple.parser.ParseException;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class Controller {


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
    private AutoFillTextBox<ObservableList<Airport>> inputOrigin;
    @FXML
    private AutoFillTextBox<ObservableList<Airport>> inputDestination;
    @FXML
    private TextField inputDepartTime;
//    @FXML
//    private String outputFlightTime;


    public void initialize() {
        boardingPassData = new BoardingPass();
        inputGenderSelection.getItems().addAll("Male", "Female");
        ObservableList<Airport> data = FXCollections.observableArrayList();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Airports.getInstance().fetchAirports();
                    data.addAll(Airports.getInstance().getAirportList());
                    System.out.println("It worked?");
                    inputDestination.addData(data);
                    inputOrigin.addData(data);
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