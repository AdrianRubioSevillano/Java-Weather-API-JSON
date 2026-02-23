package es.upsa.ssbbdd2.trabajo1.predicates;

import es.upsa.ssbbdd2.trabajo1.model.City;

import java.util.function.Predicate;

public class Predicates {
    private Predicates() {}

    public static Predicate<City> cityHasName(String name) {
        return new CityHasNamePredicate(name);
    }

    public static Predicate<City> cityBelongsTo(String state) {
        return new CityBelongsToTheState(state);
    }

    public static Predicate<City> cityHasPopulationGreaterThan(int population) {
        return new CityHasPopulationGreaterThanPredicate(population);
    }

    public static Predicate<City> cityHasOtherName(String languageCode, String name) {
        return new CityHasOtherNamePredicate(languageCode, name);
    }
}