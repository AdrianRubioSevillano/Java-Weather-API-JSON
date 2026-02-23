package es.upsa.ssbbdd2.trabajo1;

import es.upsa.ssbbdd2.trabajo1.model.City;
import es.upsa.ssbbdd2.trabajo1.model.CityWithWeather;
import es.upsa.ssbbdd2.trabajo1.model.Weather;
import es.upsa.ssbbdd2.trabajo1.openweather.OpenWeatherApi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class UseCase {

    private final String apiKey;

    public UseCase(String apiKey) {
        this.apiKey = apiKey;
    }

    public void generarFicheros(File file, Predicate<City> predicate, File newFile, NdjsonIO ndjsonIO, JsonParser jsonParser) throws Exception {

        OpenWeatherApi api = OpenWeatherApi.of(this.apiKey);

        List<City> listaCity = ndjsonIO.read(file, predicate);
        List<CityWithWeather> lista = new ArrayList<>();

        for (City city : listaCity) {
            try {
                String jsonString = api.requestCurrentWeather(
                        city.getLocation().getLatitude(),
                        city.getLocation().getLongitude()
                );
                Weather weather = jsonParser.readWeather(jsonString);

                CityWithWeather item = CityWithWeather.builder()
                        .withCity(city)
                        .withWeather(weather)
                        .build();

                lista.add(item);
            } catch (Exception e) {
                throw new Exception(e.getMessage());

            }

        }

        ndjsonIO.write(lista, newFile);
    }



}