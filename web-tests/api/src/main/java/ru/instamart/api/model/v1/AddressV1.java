package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddressV1 extends BaseObject {
    private Integer id;
    @JsonProperty("full_address")
    private String fullAddress;
    private String city;
    private String street;
    private String building;
    private String block;
    private Object floor;
    private String apartment;
    private Object entrance;
    private Boolean elevator;
    private String region;
    private String comments;
    private String phone;
    private String area;
    private String settlement;
    private Double lat;
    private Double lon;
    @JsonProperty("city_kladr_id")
    private Object cityKladrId;
    @JsonProperty("street_kladr_id")
    private Object streetKladrId;
    @JsonProperty("user_id")
    private Object userId;
    @JsonProperty("door_phone")
    private String doorPhone;
    private Object kind;
    @JsonProperty("delivery_to_door")
    private Boolean deliveryToDoor;
}
