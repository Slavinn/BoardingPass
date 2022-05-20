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
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.json.simple.parser.ParseException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;
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
    private ChoiceBox<LocalTime> inputDepartTime;
    @FXML
    private Button estimate_btn;




    public void initialize() {
        estimate_btn.setDisable(true);
        inputOrigin.setDisable(true);
        inputDestination.setDisable(true);
        boardingPassData = new BoardingPass();
        inputGenderSelection.getItems().addAll("Male", "Female");
        inputDepartTime.getItems().addAll(LocalTime.of(6, 30), LocalTime.of(8, 35), LocalTime.of(11, 10), LocalTime.of(14, 30), LocalTime.of(20, 50));
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
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Airports.getInstance().fetchAirports();

                    for (Airport airport : Airports.getInstance().getAirportList()) {
                        airportNames.add(airport.getName().toLowerCase(Locale.ROOT));

                    }
                    inputOrigin.getEntries().addAll(airportNames);
                    inputDestination.getEntries().addAll(airportNames);
                    inputOrigin.setDisable(false);
                    inputDestination.setDisable(false);
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
                if (airportNames.contains(inputOrigin.getText()) && airportNames.contains(inputDestination.getText()) && boardingPassData.getNormalPrice() == null) {
                    System.out.println("estimate plane ticket");
                    Airports airports = Airports.getInstance();

                    boardingPassData
                            .getFlight()
                            .setOrigin(airports
                                    .getAirportByName(inputOrigin.getText()));

                    boardingPassData
                            .getFlight()
                            .setDestination(airports
                                    .getAirportByName(inputDestination.getText()));
                    boardingPassData.getFlight().fetchDistance();
                    estimate_btn.setDisable(false);
                    boardingPassData.setNormalPrice();
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
        int validate = 0;
        if (inputName.getText().isEmpty()) {
            inputName.requestFocus();
        } else {
            boardingPassData.setName(inputName.getText());
            validate++;
        }
        if (inputAge.getText().isEmpty()) {
            inputAge.requestFocus();
        } else {
            boardingPassData.setAge(Integer.parseInt(inputAge.getText()));
            validate++;
        }
        if (inputGenderSelection.getValue() == null) {
            inputGenderSelection.show();
            inputPhoneNumber.requestFocus();
        } else {
            boardingPassData.setGender(inputGenderSelection.getValue());
            validate++;
        }
        if (inputPhoneNumber.getText().isEmpty()) {
            inputPhoneNumber.requestFocus();
        } else {
            boardingPassData.setPhoneNumber(inputPhoneNumber.getText());
            validate++;
        }
        if (inputEmail.getText().isEmpty()) {
            inputEmail.requestFocus();
        } else {
            boardingPassData.setEmail(inputEmail.getText());
            validate++;
        }
        if (inputDate.getValue() == null) {
            inputDate.requestFocus();
        } else {
            boardingPassData.getFlight().setDepartureDate(inputDate.getValue());
            validate++;
        }
        if (inputOrigin.getText().isEmpty()) {
            inputOrigin.requestFocus();
        } else {
            validate++;
        }
        if (inputDestination.getText().isEmpty()) {
            inputDestination.requestFocus();
        } else {
            validate++;
        }
        if (inputDepartTime.getValue() == null) {
            inputDepartTime.requestFocus();
        } else {
            boardingPassData.getFlight().setDepartureTime(inputDepartTime.getValue());
            validate++;
        }


        if (validate == 9) {
            boardingPassData.setPriceDiscounts(boardingPassData.getNormalPrice().doubleValue());
            showEstimates();
        }


    }

    public void showEstimates() {
        outputFlightTime.setText(boardingPassData.getFlight().getDepartureTime().toString());
        outputArrivalTime.setText(boardingPassData.getFlight().getArrivalTime().toString());
        outputFlightDuration.setText(boardingPassData.getFlight().getFlightDuration().toString());

        outputAgeDiscountAmount.setText(String.valueOf(boardingPassData.getAgeDiscount()));
        outputLadiesDiscountAmount.setText(String.valueOf(boardingPassData.getFemaleDiscount()));
        outputTicketPrice.setText(String.valueOf(boardingPassData.getNormalPrice()));
        outputTotalCost.setText(String.valueOf(boardingPassData.getDiscountPrice()));
        flightEstimateBox.setOpacity(1);
    }

}