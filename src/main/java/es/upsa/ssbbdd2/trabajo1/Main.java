//Adrián Rubio Sevillano
//Sistemas de Bases de Datos II
//Trabajo-I Convocatoria Ordinaría

package es.upsa.ssbbdd2.trabajo1;

import es.upsa.ssbbdd2.trabajo1.impl.JsonbParser;
import es.upsa.ssbbdd2.trabajo1.impl.JsonpParser;
import es.upsa.ssbbdd2.trabajo1.impl.NdjsonIOImpl;
import es.upsa.ssbbdd2.trabajo1.model.City;
import es.upsa.ssbbdd2.trabajo1.openweather.OpenWeatherApi;
import es.upsa.ssbbdd2.trabajo1.predicates.CityBelongsToTheState;
import es.upsa.ssbbdd2.trabajo1.predicates.CityHasPopulationGreaterThanPredicate;

import java.io.File;
import java.util.function.Predicate;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        File file = new File("cities.ndjson");
        File jsonbFile = new File("cities_jsonb.ndjson");
        File jsonpFile = new File("cities_jsonp.ndjson");

        OpenWeatherApi openWeatherApi = OpenWeatherApi.of("7edc0db8f107b458113d5b0a7faa2c07");
        String weatherJSON = openWeatherApi.requestCurrentWeather(40.9651572, -5.6640182);

        Predicate<City> predicate = new CityBelongsToTheState("Castilla y León").and(new CityHasPopulationGreaterThanPredicate(75000));

        UseCase useCase = new UseCase("7edc0db8f107b458113d5b0a7faa2c07");

        JsonParser jsonbParser = new JsonbParser();
        NdjsonIO ndjsonBIO = new NdjsonIOImpl(jsonbParser);

        useCase.generarFicheros(file, predicate, jsonbFile, ndjsonBIO, jsonbParser);

        JsonParser jsonpParser = new JsonpParser();
        NdjsonIO ndjsonPIO = new NdjsonIOImpl(jsonpParser);

        useCase.generarFicheros(file, predicate, jsonpFile, ndjsonPIO, jsonpParser);

    }



}