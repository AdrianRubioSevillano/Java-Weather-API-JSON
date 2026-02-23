package es.upsa.ssbbdd2.trabajo1.impl;

import es.upsa.ssbbdd2.trabajo1.JsonParser;
import es.upsa.ssbbdd2.trabajo1.model.Address;
import es.upsa.ssbbdd2.trabajo1.model.City;
import es.upsa.ssbbdd2.trabajo1.model.CityWithWeather;
import es.upsa.ssbbdd2.trabajo1.model.Location;
import es.upsa.ssbbdd2.trabajo1.model.Weather;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonWriter;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class JsonpParser implements JsonParser {

    @Override
    public City readCity(String jsonCity) {
        try (JsonReader reader = Json.createReader(new StringReader(jsonCity))) {
            JsonObject json = reader.readObject();

            JsonObject jsonAddress = json.getJsonObject("address");
            Address address = Address.builder()
                    .withCity(jsonAddress.getString("city", null))
                    .withProvince(jsonAddress.getString("province", null))
                    .withState(jsonAddress.getString("state", null))
                    .withCountry(jsonAddress.getString("country", null))
                    .withCountryCode(jsonAddress.getString("country_code", null))
                    .build();

            JsonArray jsonLocation = json.getJsonArray("location");
            Location location = Location.builder()
                    .withLongitude(jsonLocation.getJsonNumber(0).doubleValue())
                    .withLatitude(jsonLocation.getJsonNumber(1).doubleValue())
                    .build();

            Map<String, String> otherNames = new HashMap<>();
            if (json.containsKey("other_names")) {
                JsonObject jsonOtherNames = json.getJsonObject("other_names");
                for (String key : jsonOtherNames.keySet()) {
                    otherNames.put(key, jsonOtherNames.getString(key));
                }
            }

            long population = 0;
            if (json.containsKey("population") && !json.isNull("population")) {
                population = json.getJsonNumber("population").longValue();
            }

            return City.builder()
                    .withName(json.getString("name", null))
                    .withDisplayName(json.getString("display_name", null))
                    .withOtherNames(otherNames)
                    .withPopulation(population)
                    .withAddress(address)
                    .withLocation(location)
                    .build();
        }
    }

    @Override
    public Weather readWeather(String jsonWeather) {
        try (JsonReader reader = Json.createReader(new StringReader(jsonWeather))) {
            JsonObject json = reader.readObject();

            JsonObject main = json.getJsonObject("main");
            JsonObject wind = json.getJsonObject("wind");
            JsonObject clouds = json.getJsonObject("clouds");
            JsonObject sys = json.getJsonObject("sys");
            JsonObject weatherItem = json.getJsonArray("weather").getJsonObject(0);

            return Weather.builder()
                    .withDescription(weatherItem.getString("description"))
                    .withTemp(main.getJsonNumber("temp").doubleValue())
                    .withFeelsLike(main.getJsonNumber("feels_like").doubleValue())
                    .withHumidity(main.getJsonNumber("humidity").doubleValue())
                    .withWindSpeed(wind.getJsonNumber("speed").doubleValue())
                    .withClouds(clouds.getJsonNumber("all").doubleValue())
                    .withDateTime(unixToLocalDateTime(json.getJsonNumber("dt").longValue()))
                    .withSunrise(unixToLocalDateTime(sys.getJsonNumber("sunrise").longValue()))
                    .withSunset(unixToLocalDateTime(sys.getJsonNumber("sunset").longValue()))
                    .build();
        }
    }

    @Override
    public String write(CityWithWeather cityWithWeather) {
        City city = cityWithWeather.getCity();
        Weather weather = cityWithWeather.getWeather();

        JsonObjectBuilder cityBuilder = Json.createObjectBuilder()
                .add("name", city.getName() != null ? city.getName() : "")
                .add("population", city.getPopulation());

        if (city.getDisplayName() != null) {
            cityBuilder.add("display_name", city.getDisplayName());
        }

        if (city.getOtherNames() != null && !city.getOtherNames().isEmpty()) {
            JsonObjectBuilder otherNamesBuilder = Json.createObjectBuilder();
            city.getOtherNames().forEach(otherNamesBuilder::add);
            cityBuilder.add("other_names", otherNamesBuilder);
        }

        cityBuilder.add("address", Json.createObjectBuilder()
                .add("city", city.getAddress().getCity() != null ? city.getAddress().getCity() : "")
                .add("province", city.getAddress().getProvince() != null ? city.getAddress().getProvince() : "")
                .add("state", city.getAddress().getState() != null ? city.getAddress().getState() : "")
                .add("country", city.getAddress().getCountry() != null ? city.getAddress().getCountry() : "")
                .add("country_code", city.getAddress().getCountryCode() != null ? city.getAddress().getCountryCode() : ""));

        cityBuilder.add("location", Json.createArrayBuilder()
                .add(city.getLocation().getLongitude())
                .add(city.getLocation().getLatitude()));

        JsonObject cityJson = cityBuilder.build();


        JsonObject weatherJson = Json.createObjectBuilder()
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

        JsonObject CityWithWeather = Json.createObjectBuilder()
                .add("city", cityJson)
                .add("weather", weatherJson)
                .build();

        StringWriter writer = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(writer)) {
            jsonWriter.writeObject(CityWithWeather);
        }
        return writer.toString();
    }

    private LocalDateTime unixToLocalDateTime(long seconds) {
        return Instant.ofEpochSecond(seconds)
                .atZone(ZoneId.of("Europe/Madrid"))
                .toLocalDateTime();
    }
}