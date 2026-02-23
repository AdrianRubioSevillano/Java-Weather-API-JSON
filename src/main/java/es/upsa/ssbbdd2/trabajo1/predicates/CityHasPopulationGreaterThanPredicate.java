package es.upsa.ssbbdd2.trabajo1.predicates;
import es.upsa.ssbbdd2.trabajo1.model.City;

import lombok.AllArgsConstructor;

import java.util.function.Predicate;

@AllArgsConstructor
public class CityHasPopulationGreaterThanPredicate implements Predicate<City> {

    private final long population;

    @Override
    public boolean test(City city) {
        return city.getPopulation() > population;
    }
}