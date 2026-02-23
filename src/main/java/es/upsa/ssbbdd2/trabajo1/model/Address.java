package es.upsa.ssbbdd2.trabajo1.model;

import jakarta.json.bind.annotation.JsonbProperty;
import lombok.*;

@Data
@Builder(setterPrefix = "with")
@With
@AllArgsConstructor
@NoArgsConstructor

public class Address {

    private String city;
    private String province;
    private String state;
    private String country;
    @JsonbProperty("country_code")
    private String countryCode;
    private String archipelago;

}
