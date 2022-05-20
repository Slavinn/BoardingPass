package com.boardingpass.boardingpass.datamodel;

import org.json.simple.parser.ParseException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Flight {

    private double distance;
    private LocalTime flightDuration;

    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private LocalDate departureDate;

    public Flight() {
        this.distance = 0;
        this.flightDuration = null;
        this.arrivalTime = null;
        this.departureTime = null;
        this.departureDate = null;
        this.origin = null;
        this.destination = null;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    private Airport origin;
    private Airport destination;


    public double getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance ; // km/h
    }

    public LocalTime getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(double distance) {
        BigDecimal time = BigDecimal.valueOf(distance / 900)
                .setScale(2, RoundingMode.HALF_UP);

        int hour = (int)Math.floor(time.doubleValue());
        int min = (int)((time.doubleValue()- hour ) * 100);
        this.flightDuration = LocalTime.of(hour, min);
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime() {
        int hours = this.getDepartureTime().getHour() + this.getFlightDuration().getHour();
        int min = this.getDepartureTime().getMinute() + this.getFlightDuration().getMinute();
        if (min > 59) {
            hours +=min /60;
            min = min % 60;
        }
        this.arrivalTime = LocalTime.of(hours, min);
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
        setArrivalTime();
    }

    public Airport getOrigin() {
        return origin;
    }

    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public void fetchDistance() {

        // Haversine formula to calculate distance between latitudes and longitudes taken
        // Author https://www.geeksforgeeks.org
        double dLat = Math.toRadians(this.origin.getLat() - this.destination.getLat()); //lat2 - lat1
        double dLon = Math.toRadians(this.origin.getLng() - this.destination.getLng()); //lon2 - lon1

        // convert to radians
        double lat1 = Math.toRadians(this.origin.getLat());
        double lat2 = Math.toRadians(this.destination.getLat());

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        double distance = rad * c;
        // set distance
        setDistance((int)distance);
        setFlightDuration(distance);
    }
}
