package com.boardingpass.boardingpass.datamodel;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;


public class BoardingPass {
    /*
    Name
    Email
    Phone Number
    Gender
    Age
    //   //
    Date
    Destination
    Departure
    Time
    */

    // Passenger details
    private String name;
    private String email;
    private String phoneNumber;
    private boolean gender; // true means the gender is a female
    private Integer age;


    /// Fight details
    private String boardingPassNumber;
    private String departureDate;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime eta;


    // Ticket Price
    private int price;

    public BoardingPass() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(String gender) {
        this.gender = gender.equals("female");
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    private void setBoardingPassNumber(String boardingPassNumber) {
        this.boardingPassNumber = boardingPassNumber;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate =  departureDate;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setEta(LocalDateTime eta) {
        this.eta = eta;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void generateBoardingPassNumber() throws NoSuchAlgorithmException {
        HashCreator hash = new HashCreator();
        setBoardingPassNumber(
                hash.createMD5Hash(this.name+this.departureDate));
    }

    @Override
    public String toString() {
        return String.format("name: %s\n age: %s\n isFemale: %b\n phoneNumber: %s\n email: %s"
                , name, age, gender, phoneNumber, email);
    }
}
