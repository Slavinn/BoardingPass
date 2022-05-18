package com.boardingpass.boardingpass.datamodel;

public class Airport {
    public String icao_code;
    public String country_code;
    public String iata_code;
    public double lng;
    public String name;
    public double lat;

    public Airport() {
        this.icao_code = null;
        this.country_code = null;
        this.iata_code = null;
        this.lng = 0;
        this.name = null;
        this.lat = 0;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name + " " + this.icao_code;
    }
}
