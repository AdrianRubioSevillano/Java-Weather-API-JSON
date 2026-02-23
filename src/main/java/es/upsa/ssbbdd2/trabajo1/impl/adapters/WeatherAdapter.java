package es.upsa.ssbbdd2.trabajo1.impl.adapters;

import es.upsa.ssbbdd2.trabajo1.model.Weather;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.bind.adapter.JsonbAdapter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;



public class WeatherAdapter implements JsonbAdapter<Weather, JsonObject> {
    private static final ZoneId ZONE_ID = ZoneId.of("Europe/Madrid");

    @Override
    public Weather adaptFromJson(JsonObject json) throws Exception {

        String description = json.getJsonArray("weather")
                .getJsonObject(0)
                .getString("description");

        JsonObject main = json.getJsonObject("main");
        double temp = main.getJsonNumber("temp").doubleValue();
        double feelsLike = main.getJsonNumber("feels_like").doubleValue();
        double humidity = main.getJsonNumber("humidity").doubleValue();
        double windSpeed = json.getJsonObject("wind").getJsonNumber("speed").doubleValue();
        double clouds = json.getJsonObject("clouds").getJsonNumber("all").doubleValue();
        long dt = json.getJsonNumber("dt").longValue();
        long sunrise = json.getJsonObject("sys").getJsonNumber("sunrise").longValue();
        long sunset = json.getJsonObject("sys").getJsonNumber("sunset").longValue();

        return Weather.builder()
                .withDescription(description)
                .withTemp(temp)
                .withFeelsLike(feelsLike)
                .withHumidity(humidity)
                .withWindSpeed(windSpeed)
                .withClouds(clouds)
                .withDateTime(unixToLocalDateTime(dt))
                .withSunrise(unixToLocalDateTime(sunrise))
                .withSunset(unixToLocalDateTime(sunset))
                .build();
    }

    @Override
    public JsonObject adaptToJson(Weather weather) throws Exception {

        return Json.createObjectBuilder()
                .add("dt", weather.getDateTime().toString())
                .add("description", weather.getDescription())
                .add("temp", weather.getTemp())
                .add("feels_like", weather.getFeelsLike())
                .add("humidity", weather.getHumidity())
                .add("wind_speed", weather.getWindSpeed())
                .add("clouds", weather.getClouds())
                .add("sunrise", weather.getSunrise().toString())
                .add("sunset", weather.getSunset().toString())
                .build();
    }

    private LocalDateTime unixToLocalDateTime(long seconds) {
        return Instant.ofEpochSecond(seconds)
                .atZone(ZONE_ID)
                .toLocalDateTime();
    }
}