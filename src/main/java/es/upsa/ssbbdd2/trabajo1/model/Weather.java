package es.upsa.ssbbdd2.trabajo1.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder(setterPrefix = "with")
@With
@AllArgsConstructor
@NoArgsConstructor

public class Weather {

    private String description;
    private double temp;
    private double feelsLike;
    private double humidity;
    private double windSpeed;
    private double clouds;
    private LocalDateTime dateTime;
    private LocalDateTime sunrise;
    private LocalDateTime sunset;

}
