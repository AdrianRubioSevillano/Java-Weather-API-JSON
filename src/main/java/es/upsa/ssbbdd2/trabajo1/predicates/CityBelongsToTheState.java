package es.upsa.ssbbdd2.trabajo1.predicates;
import es.upsa.ssbbdd2.trabajo1.model.City;

import lombok.AllArgsConstructor;

import java.util.function.Predicate;

@AllArgsConstructor
public class CityBelongsToTheState implements Predicate<City> {

    private final String state;

    @Override
    public boolean test(City city) {
        String cityState = city.getAddress().getState();
        if (cityState != null) {
            return cityState.equalsIgnoreCase(state);
        }

        String archipelago = city.getAddress().getArchipelago();
        return archipelago != null && archipelago.equalsIgnoreCase(state);
    }
}