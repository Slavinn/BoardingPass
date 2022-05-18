package com.boardingpass.boardingpass;

import com.boardingpass.boardingpass.datamodel.Airport;
import com.boardingpass.boardingpass.datamodel.Airports;
import com.boardingpass.boardingpass.datamodel.BoardingPass;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.json.simple.parser.ParseException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Controller {
    public GridPane flightEstimateBox;

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
        inputAge.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    inputAge.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        inputPhoneNumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    inputAge.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

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
        appLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(airportNames.contains(inputOrigin.getText()) && airportNames.contains(inputDestination.getText()) && boardingPassData.getPrice() == 0) {
                    System.out.println("estimate plane ticket");
                    boardingPassData.setOrigin(Airports.getInstance()
                                    .getAirportByName(inputOrigin.getText()));
                    boardingPassData.setDestination(Airports.getInstance()
                            .getAirportByName(inputDestination.getText()));

                    boardingPassData.setPrice(-1);

                    Runnable getDistance = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                boardingPassData.getFlight().fetchDistance(boardingPassData.getOrigin(), boardingPassData.getDestination());
                            } catch (ExecutionException | InterruptedException | ParseException e) {
                                throw new RuntimeException(e);
                            }
                            boardingPassData.setPrice(250);
//                      boardingPassData.getPriceDiscounts(250);
                            System.out.println(boardingPassData.getPrice());
                        }
                    };
                    new Thread(getDistance).start();



                }
            }
        };

        appLoop.start();

        new Thread(task).start();

    }

    public void onBookFlightButtonPress() {
        // write data to file using boardingPassData.toString()
    }

    public void onEstimateButtonPress() {
        if (inputName.getText().isEmpty()) {
            inputName.requestFocus();
        } else {
            boardingPassData.setName(inputName.getText());
        }
        if (inputAge.getText().isEmpty()) {
            inputAge.requestFocus();
        } else {
            boardingPassData.setAge(Integer.parseInt(inputAge.getText()));
        }
        if (inputGenderSelection.getValue() == null) {
            inputGenderSelection.show();
            inputPhoneNumber.requestFocus();
        } else {
            boardingPassData.setGender(inputGenderSelection.getValue());
        }
        if (inputPhoneNumber.getText().isEmpty()) {
            inputPhoneNumber.requestFocus();
        } else {
            boardingPassData.setPhoneNumber(inputPhoneNumber.getText());
        }
        if(inputEmail.getText().isEmpty()) {
            inputEmail.requestFocus();
        } else {
            boardingPassData.setEmail(inputEmail.getText());
        }
        if (inputDate.getValue() == null ){
            inputDate.requestFocus();
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
            String formattedString = inputDate.getValue().format(formatter);
            boardingPassData.setDepartureDate(formattedString);
        }
        if(inputOrigin.getText().isEmpty()) {
            inputOrigin.requestFocus();
        } else {
            Airport airport = Airports.getInstance().getAirportByName(inputOrigin.getText());
            boardingPassData.setOrigin(airport);
        }
        if (inputDestination.getText().isEmpty()) {
            inputDestination.requestFocus();
        } else {
            Airport airport = Airports.getInstance().getAirportByName(inputDestination.getText());
            boardingPassData.setDestination(airport);
        }
        if (inputDepartTime.getValue() == null) {
            inputDepartTime.requestFocus();
        } else {
            boardingPassData.setDepartureTime(inputDepartTime.getValue());
        }


        String stringPrice = String.format("Estimate: $ %.2f",boardingPassData.getPrice());

        outputEstimate.setText(stringPrice);

        // This needs some sort of a check to make sure all fields are validated
        // I was just messing with field validation and focusing afield that was filled out
//        showEstimates();


        
    }

    public void showEstimates(){
            flightEstimateBox.setOpacity(1);
    }
    
}