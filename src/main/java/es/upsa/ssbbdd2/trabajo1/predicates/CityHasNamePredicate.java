package es.upsa.ssbbdd2.trabajo1.predicates;

import es.upsa.ssbbdd2.trabajo1.model.City;
import lombok.AllArgsConstructor;

import java.util.function.Predicate;

@AllArgsConstructor
public class CityHasNamePredicate implements Predicate<City> {

    private final String name;

    @Override
    public boolean test(City city) {
        return name.equals(city.getName());
    }
}