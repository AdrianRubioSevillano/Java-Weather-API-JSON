package es.upsa.ssbbdd2.trabajo1.model;

import lombok.*;

@Data
@Builder(setterPrefix = "with")
@With
@AllArgsConstructor
@NoArgsConstructor

public class Location {

    private double longitude;
    private double latitude;

}
