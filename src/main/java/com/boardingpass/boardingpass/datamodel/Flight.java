package com.boardingpass.boardingpass.datamodel;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Flight {

    private int distance;
    private int flightDuration;

    private int arrivalTime;
    private int departureTime;

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime() {
        this.arrivalTime = this.departureTime + this.flightDuration;
    }

    public int getDistance() {
        return distance;
    }

    public int getFlightDuration() {
        return flightDuration;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setFlightDuration() {
        this.flightDuration =  distance / 900; // km/h average flight speed
    }



    public void fetchDistance(Airport origin, Airport destination) throws ExecutionException, InterruptedException, ParseException {
        String url = String.format("https://www.distance24.org/route.json?stops=%s|%s", origin.getIata_code(), destination.getIata_code());

        AsyncHttpClient client = new DefaultAsyncHttpClient();
        CompletableFuture<Response> response = client.prepare("GET", url)
                .execute()
                .toCompletableFuture();

        response.join();
        String responseString = response.get().getResponseBody();
        JSONParser parse = new JSONParser();
        JSONObject data_obj = (JSONObject) parse.parse(responseString);
//        Get the required object from the above created object
        int distance = Integer.parseInt(data_obj.get("distance").toString());
        setDistance(distance);
        setFlightDuration();

    }
}
