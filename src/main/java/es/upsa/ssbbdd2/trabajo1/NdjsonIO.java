package es.upsa.ssbbdd2.trabajo1;

import es.upsa.ssbbdd2.trabajo1.model.City;
import es.upsa.ssbbdd2.trabajo1.model.CityWithWeather;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public interface NdjsonIO {

    public List<City> read(File file, Predicate<City> predicate) throws IOException;
    public void write(List<CityWithWeather> citiesWithWeather, File file) throws IOException;

}
