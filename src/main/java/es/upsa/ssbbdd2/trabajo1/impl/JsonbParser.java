package es.upsa.ssbbdd2.trabajo1.impl;

import es.upsa.ssbbdd2.trabajo1.JsonParser;
import es.upsa.ssbbdd2.trabajo1.impl.adapters.LocationAdapter;
import es.upsa.ssbbdd2.trabajo1.impl.adapters.WeatherAdapter;
import es.upsa.ssbbdd2.trabajo1.model.City;
import es.upsa.ssbbdd2.trabajo1.model.CityWithWeather;
import es.upsa.ssbbdd2.trabajo1.model.Weather;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;



public class JsonbParser implements JsonParser {

    private final Jsonb jsonb;

    public JsonbParser() {
        this.jsonb = createJsonb();
    }

    @Override
    public City readCity(String jsonCity) {
        return jsonb.fromJson(jsonCity, City.class);
    }

    @Override
    public Weather readWeather(String jsonWeather) {
        return jsonb.fromJson(jsonWeather, Weather.class);
    }

    @Override
    public String write(CityWithWeather cityWithWeather) {
        return jsonb.toJson(cityWithWeather);
    }

    private Jsonb createJsonb() {
        JsonbConfig jsonbConfig = new JsonbConfig()
                .withAdapters(new LocationAdapter(), new WeatherAdapter());

        return JsonbBuilder.newBuilder()
                .withConfig(jsonbConfig)
                .build();
    }

}