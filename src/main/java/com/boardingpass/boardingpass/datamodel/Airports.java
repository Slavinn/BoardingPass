package com.boardingpass.boardingpass.datamodel;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Airports {
    public static Airports instance = new Airports();

    private ArrayList<Airport> airportList = new ArrayList<>();



    public static Airports getInstance() {
        return  instance;
    }

    public ArrayList<Airport> getAirportList() {
        return airportList;
    }

    public Airport getAirportByName(String airPortName) {
        for (Airport airport : airportList) {
            if(airport.getName().toLowerCase(Locale.ROOT).equals(airPortName)) {
                return airport;
            }
        }
        return null;
    }

    public void fetchAirports() throws ExecutionException, InterruptedException, ParseException, JsonProcessingException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        CompletableFuture<Response> response = client.prepare("GET", "https://airlabs.co/api/v9/airports?country_code=US&api_key=91bdebc2-0936-4e70-af69-37e143e84e4d")
                .execute()
                .toCompletableFuture();

        response.join();

        String responseString = response.get().getResponseBody();

        JSONParser parse = new JSONParser();
        JSONObject data_obj = (JSONObject) parse.parse(responseString);
//        Get the required object from the above created object
        JSONArray arr = (JSONArray) data_obj.get("response");

        Gson gson = new Gson();
        ObjectMapper mapper = new ObjectMapper();
        for (int i = 0; i < arr.size(); i++) {
            String jsonString = gson.toJson(arr.get(i));
            airportList.add(mapper.readValue(jsonString, Airport.class));
        }


    }


}
