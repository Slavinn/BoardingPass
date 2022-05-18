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


    private Flight flight;
    /// Fight details
    private String boardingPassNumber;
    private String departureDate;
    private Airport origin;
    private Airport destination;
    private String departureTime;
    private String eta;

    private double childDiscount;
    private double elderDiscount;
    private double femaleDiscount;


    // Ticket Price
    private double price;

    public Flight getFlight() {
        return flight;
    }

    public BoardingPass() {
        this.flight = new Flight();
        this.name = "";
        this.email = "";
        this.phoneNumber = "";
        this.gender = false;
        this.age = 0;
        this.boardingPassNumber = "";
        this.departureDate = "";
        this.origin = null;
        this.destination = null;
        this.departureTime = null;
        this.eta = null;
        this.childDiscount = 0;
        this.elderDiscount = 0;
        this.femaleDiscount = 0;
        this.price = 0;
    }

    public double getPrice() {
        return price;
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

    public void setGender(String genderInput) {
        this.gender = genderInput.equals("female");
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

    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public Airport getOrigin() {
        return origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public void getPriceDiscounts(int price) {
        if(this.age <= 12) {
            this.childDiscount = price * 0.5;
        } else if (this.age >= 60) {
            this.elderDiscount = price * 0.6;
        }
        setPrice((price - childDiscount - elderDiscount));
        if (this.gender) {
            this.femaleDiscount = this.price * .25;
        }
        setPrice(price - this.femaleDiscount);

    }

    public void setPrice(double price) {
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
