package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.instamart.api.model.BaseObject;

import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressV2 extends BaseObject {
    private Integer id;
    @JsonProperty(value = "first_name")
    private String firstName;
    @JsonProperty(value = "last_name")
    private String lastName;
    @JsonProperty(value = "full_address")
    private String fullAddress;
    private String city;
    private String street;
    private String building;
    private String block;
    private String entrance;
    private String floor;
    private String apartment;
    private String comments;
    private Double lat;
    private Double lon;
    private String kind;
    @JsonProperty(value = "door_phone")
    private String doorPhone;
    @JsonProperty(value = "delivery_to_door")
    private Boolean deliveryToDoor;

    public void setCoordinates(ZoneV2 zone) {
        this.lat = zone.getLat();
        this.lon = zone.getLon();
    }

    @Override
    public String toString() {
        return new StringJoiner(
                "\n",
                fullAddress + "\n",
                "\n")
                .add("       lat: " + lat)
                .add("       lon: " + lon)
                .add("first_name: " + firstName)
                .add(" last_name: " + lastName)
                .add("        id: " + id)
                .add("door_phone: " + doorPhone)
                .add(" apartment: " + apartment)
                .add("     floor: " + floor)
                .add("  entrance: " + entrance)
                .add("     block: " + block)
                .add("  comments: " + comments)
                .toString();
    }
}
