package es.upsa.ssbbdd2.trabajo1.impl.adapters;

import es.upsa.ssbbdd2.trabajo1.model.Location;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.bind.adapter.JsonbAdapter;


public class LocationAdapter implements JsonbAdapter<Location, JsonArray> {

    @Override
    public JsonArray adaptToJson(Location location) throws Exception {
        if (location == null) {
            return null;
        }
        return Json.createArrayBuilder()
                .add(location.getLongitude())
                .add(location.getLatitude())
                .build();
    }

    @Override
    public Location adaptFromJson(JsonArray jsonArray) throws Exception {

        double longitude = jsonArray.getJsonNumber(0).doubleValue();
        double latitude = jsonArray.getJsonNumber(1).doubleValue();

        return Location.builder()
                .withLongitude(longitude)
                .withLatitude(latitude)
                .build();

    }

}