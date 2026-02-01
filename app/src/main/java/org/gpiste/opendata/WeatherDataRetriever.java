package org.gpiste.opendata;

import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class WeatherDataRetriever {
    private final String API_KEY = "bbe0a8c8dd20d19a0b5c37f1b07bc68f";
    private final String CONVERTER_BASE_URL = "https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=5&appid=%s";

    private final String WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s";

    public WeatherData getWeatherData(String municipality){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode areas = null;
            try {
                areas = objectMapper.readTree(new URL(String.format(CONVERTER_BASE_URL, municipality, API_KEY)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            Log.d("LUT", areas.toPrettyString());

            String latitude = areas.get(0).get("lat").toString();
            String longitude = areas.get(0).get("lon").toString();

            JsonNode weatherData;

            try {
                weatherData = objectMapper.readTree(new URL(String.format(WEATHER_BASE_URL, latitude, longitude, API_KEY)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Log.d("LUT", weatherData.toPrettyString());

            WeatherData wd = new WeatherData(
                    weatherData.get("name").asText(),
                    weatherData.get("weather").get(0).get("main").asText(),
                    weatherData.get("weather").get(0).get("description").asText(),
                    weatherData.get("main").get("temp").asText(),
                    weatherData.get("wind").get("speed").asText()
            );
            return wd;
        } catch(Exception e) {

        }
        return null;

    }

}
