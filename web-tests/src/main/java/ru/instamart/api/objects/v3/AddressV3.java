package ru.instamart.api.objects.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddressV3 extends BaseObject {
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
    @JsonProperty("door_phone")
    private String doorPhone;
    @JsonProperty("delivery_to_door")
    private Boolean deliveryToDoor;
}
