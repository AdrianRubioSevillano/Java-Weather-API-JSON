package es.upsa.ssbbdd2.trabajo1.impl;

import es.upsa.ssbbdd2.trabajo1.JsonParser;
import es.upsa.ssbbdd2.trabajo1.NdjsonIO;
import es.upsa.ssbbdd2.trabajo1.model.City;
import es.upsa.ssbbdd2.trabajo1.model.CityWithWeather;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class NdjsonIOImpl implements NdjsonIO {

    private final JsonParser jsonParser;

    public NdjsonIOImpl(JsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    @Override
    public List<City> read(File file, Predicate<City> predicate) throws IOException {
        List<City> cities = new ArrayList<>();

        try (
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                City city = jsonParser.readCity(line);
                if (predicate.test(city)) {
                    cities.add(city);
                }
            }
        }

        return cities;
    }

    @Override
    public void write(List<CityWithWeather> citiesWithWeather, File file) throws IOException {
        try (
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw)
        ) {
            for (CityWithWeather item : citiesWithWeather) {
                String jsonLine = jsonParser.write(item);
                bw.write(jsonLine);
                bw.newLine();
            }
        }
    }
}
