package es.upsa.ssbbdd2.trabajo1.model;

import lombok.*;

@Data
@Builder(setterPrefix = "with")
@With
@AllArgsConstructor
@NoArgsConstructor

public class CityWithWeather {

    private City city;
    private Weather weather;

}
