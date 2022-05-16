package com.boardingpass.boardingpass.datamodel;

import java.util.ArrayList;

public class Airports {
    private static Airports instance = new Airports();

    private ArrayList<String> airportList;


    public static Airports getInstance() {
        return  instance;
    }

    public ArrayList<String> getAirportList() {
        return airportList;
    }

    public void setAirportList(ArrayList<String> airportList) {
        this.airportList = airportList;
    }

}
