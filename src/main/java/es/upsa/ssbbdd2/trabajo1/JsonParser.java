package es.upsa.ssbbdd2.trabajo1;

import es.upsa.ssbbdd2.trabajo1.model.City;
import es.upsa.ssbbdd2.trabajo1.model.CityWithWeather;
import es.upsa.ssbbdd2.trabajo1.model.Weather;

public interface JsonParser {

    public City readCity(String jsonCity);
    public Weather readWeather(String jsonWeather);
    public String write(CityWithWeather cityWithWeather);

}
