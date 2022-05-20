package com.boardingpass.boardingpass.datamodel;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Collections;


public class BoardingPass {
    /*
    Name
    Email
    Phone Number
    Gender
    Age
    //   //

    */

    // Passenger details
    private String name;
    private String email;
    private String phoneNumber;
    private String gender;
    private Integer age;

    private Flight flight;
    /// Fight details
    private String boardingPassNumber;

    // Ticket Price
    private BigDecimal ageDiscount;
    private BigDecimal femaleDiscount;

    private  BigDecimal normalPrice;
    private BigDecimal discountPrice;

    public BoardingPass() {
        this.name = "";
        this.email = "";
        this.phoneNumber = "";
        this.gender = "";
        this.age = 0;
        this.flight = new Flight();
        this.boardingPassNumber = "";
        this.ageDiscount = BigDecimal.ZERO;
        this.femaleDiscount = BigDecimal.ZERO;
        this.normalPrice = BigDecimal.ZERO;
        this.discountPrice =BigDecimal.ZERO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String isGender() {
        return gender;
    }

    public void setGender(String genderInput) {
        this.gender = genderInput;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getBoardingPassNumber() {
        return boardingPassNumber;
    }

    public void setBoardingPassNumber(String boardingPassNumber) {
        this.boardingPassNumber = boardingPassNumber;
    }

    public BigDecimal getAgeDiscount() {
        return ageDiscount;
    }

    public void setAgeDiscount(double ageDiscount) {
        this.ageDiscount = BigDecimal.valueOf(ageDiscount).setScale(2, RoundingMode.CEILING);
    }

    public BigDecimal getFemaleDiscount() {
        return femaleDiscount;
    }

    public void setFemaleDiscount(double femaleDiscount) {
        this.femaleDiscount = BigDecimal.valueOf(femaleDiscount).setScale(2, RoundingMode.CEILING);
    }

    public BigDecimal getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice() {
        this.normalPrice = BigDecimal.valueOf((this.flight.getDistance() * .15)).setScale(2, RoundingMode.CEILING); // random info

    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = BigDecimal.valueOf(discountPrice).setScale(2, RoundingMode.CEILING);
    }

    public void setPriceDiscounts(double price) {
        if(this.age <= 12) {
            setAgeDiscount(price * 0.5);
        } else if (this.age >= 60) {
            setAgeDiscount(price * 0.6);
        }
        price -= getAgeDiscount().doubleValue();
        if (this.gender.equals("Female")) {
            setFemaleDiscount(price * .25);
            price -= getFemaleDiscount().doubleValue();
        }
        setDiscountPrice(price);

    }

    public void generateBoardingPassNumber() throws NoSuchAlgorithmException {
        HashCreator hash = new HashCreator();
        setBoardingPassNumber(
                hash.createMD5Hash(this.name+this.flight.getDepartureDate().toString()+LocalDateTime.now()));
    }

    public String getGender() {
        return gender;
    }

    public void generateBoardingPassFile() throws IOException {
        File boardingPass = new File(this.boardingPassNumber);
        if (boardingPass.createNewFile()) {
            String template =  String.format(
                    "\tBOARDING PASS NUMBER %s\n"+
                            "\tPASSENGER NAME: %s\tAGE: %d \t\t\t\tGENDER: %s\n" +
                            "\tDEPARTURE DATE: %s\t ORIGIN: %s\t\t DESTINATION: %s\n" +
                            "\tDEPARTURE TIME: %s \t\t\t ARRIVAL TIME: %s\t\t\t FLIGHT DURATION: %s\n" +
                            "\tTicket Price: %s\n",
                    this.getBoardingPassNumber(),
                    this.getName(), this.getAge(), this.getGender(),
                    this.flight.getDepartureDate(), this.flight.getOrigin(), this.flight.getDestination(),
                    this.flight.getDepartureTime(), this.flight.getArrivalTime(), this.flight.getFlightDuration(),
                    this.getDiscountPrice());
            Files.write(boardingPass.toPath(), Collections.singleton(template));
        } else {
            Alert.AlertType.valueOf("Unable to generate boarding pass at this time");
        }


    }
}
