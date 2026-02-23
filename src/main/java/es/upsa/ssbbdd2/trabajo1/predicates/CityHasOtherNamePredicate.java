package es.upsa.ssbbdd2.trabajo1.predicates;

import es.upsa.ssbbdd2.trabajo1.model.City;
import lombok.AllArgsConstructor;

import java.util.function.Predicate;

@AllArgsConstructor
public class CityHasOtherNamePredicate implements Predicate<City> {

    private final String languageCode;
    private final String name;

    @Override
    public boolean test(City city) {

        String otherName = city.getOtherNames().get(languageCode);
        return otherName.equals(name);
    }
}