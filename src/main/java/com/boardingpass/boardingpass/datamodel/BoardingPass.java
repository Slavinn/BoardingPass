package com.boardingpass.boardingpass.datamodel;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        this.ageDiscount = null;
        this.femaleDiscount = null;
        this.normalPrice = null;
        this.discountPrice = null;
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
        }
        setDiscountPrice(price - getFemaleDiscount().doubleValue());

    }

    public void generateBoardingPassNumber() throws NoSuchAlgorithmException {
        HashCreator hash = new HashCreator();
        setBoardingPassNumber(
                hash.createMD5Hash(this.name+this.flight.getDepartureDate().toString()));
    }

    @Override
    public String toString() {
        return String.format("name: %s\n age: %s\n isFemale: %b\n phoneNumber: %s\n email: %s"
                , name, age, gender, phoneNumber, email);
    }

}
