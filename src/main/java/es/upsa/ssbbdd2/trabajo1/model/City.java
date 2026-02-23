package es.upsa.ssbbdd2.trabajo1.model;

import jakarta.json.bind.annotation.JsonbProperty;
import lombok.*;

import java.util.Map;

@Data
@Builder(setterPrefix = "with")
@With
@AllArgsConstructor
@NoArgsConstructor

public class City {

    private String name;
    @JsonbProperty("other_names")
    private Map<String, String> otherNames;
    @JsonbProperty("display_name")
    private String displayName;
    private long population;
    private Address address;
    private Location location;

}
