package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.*;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressV2 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "last_name")
    private String lastName;

    @JsonSchema(required = true)
    @JsonProperty(value = "full_address")
    private String fullAddress;

    @JsonSchema(required = true)
    private String city;

    @JsonSchema(required = true)
    private String street;

    @JsonSchema(required = true)
    private String building;

    @Null
    private String block;

    @Null
    private String entrance;

    @Null
    private String floor;

    @Null
    private String apartment;

    @Null
    private String comments;

    @JsonSchema(required = true)
    private Double lat;

    @JsonSchema(required = true)
    private Double lon;

    @Null
    private String kind;

    @Null
    @JsonProperty(value = "door_phone")
    private String doorPhone;

    @JsonProperty(value = "delivery_to_door")
    private Boolean deliveryToDoor;

    @Null
    private String phone;

    private Boolean elevator;

    @Null
    private String region;

    @Null
    private String area;

    @Null
    private String settlement;

    @Null
    @JsonProperty(value = "city_kladr_id")
    private Object cityKladrId;

    @Null
    @JsonProperty(value = "street_kladr_id")
    private Object streetKladrId;

    @Null
    @JsonProperty(value = "user_id")
    private Object userId;

    public void setCoordinates(ZoneV2 zone) {
        this.lat = zone.getLat();
        this.lon = zone.getLon();
    }

    public StringJoiner fullAddress() {
        return new StringJoiner(", ").add(city).add(street).add(building);
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
