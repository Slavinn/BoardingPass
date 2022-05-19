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

    Date
    Destination
    Departure
    Time
    */
    private Flight flight;
    // Passenger details
    private String name;
    private String email;
    private String phoneNumber;
    private boolean gender; // true means the gender is a female
    private Integer age;
    /// Fight details
    private String boardingPassNumber;
    private Airport origin;
    private Airport destination;
    private String departureDate;
    private String departureTime;
    private String eta;
    // Ticket cost
    private double ticketPrice;
    private double ageDiscount;
    private double femaleDiscount;
    private double totalPrice;

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

        this.ticketPrice = 0;
        this.ageDiscount = 0;
        this.femaleDiscount = 0;
        this.totalPrice = 0;

    }

    public Flight getFlight() {return flight;}
    // Passenger details
    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {this.email = email;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public void setGender(String genderInput) {this.gender = genderInput.equals("female");}
    public void setAge(Integer age) {this.age = age;}
    // Fight details
    private void setBoardingPassNumber(String boardingPassNumber) {this.boardingPassNumber = boardingPassNumber;}
    public void setDepartureDate(String departureDate) {this.departureDate =  departureDate;}
    public void setOrigin(Airport origin) {this.origin = origin;}
    public void setDestination(Airport destination) {this.destination = destination;}
    public void setDepartureTime(String departureTime) {this.departureTime = departureTime;}
    public void setEta(String eta) {this.eta = eta;}
    // Ticket cost
    public void setTicketPrice(double ticketPrice) {this.ticketPrice = ticketPrice;}
    public void setAgeDiscount(double ageDiscount) {this.ageDiscount = ageDiscount;}
    public void setFemaleDiscount(double femaleDiscount) {this.femaleDiscount = femaleDiscount;}
    public void setTotalPrice(double totalPrice){this.totalPrice = totalPrice;}


    // Passenger details
    public Integer getAge() {return age;}
    // Fight details
    public Airport getOrigin() {return origin;}
    public Airport getDestination() {return destination;}
    public String getDepartureTime() {return departureTime;}
    public String getEta(){return eta;};
    // Ticket cost
    public double getTicketPrice() {return ticketPrice;}
    public double getAgeDiscount() {return ageDiscount;}
    public double getFemaleDiscount() {return femaleDiscount;}
    public double getTotalPrice() {return totalPrice;}


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
