package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddressV1 extends BaseObject {

    @JsonSchema(required = true)
    private Integer id;

    @JsonProperty("full_address")
    private String fullAddress;

    @JsonSchema(required = true)
    private String city;

    @JsonSchema(required = true)
    private String street;

    @JsonSchema(required = true)
    private String building;

    @Null
    @JsonSchema(required = true)
    private String block;

    @Null
    @JsonSchema(required = true)
    private Object floor;

    @Null
    @JsonSchema(required = true)
    private String apartment;

    @Null
    @JsonSchema(required = true)
    private Object entrance;

    @Null
    @JsonSchema(required = true)
    private String elevator;

    @Null
    @JsonSchema(required = true)
    private String region;

    @Null
    @JsonSchema(required = true)
    private String comments;

    @Null
    @JsonSchema(required = true)
    private String phone;

    @Null
    @JsonSchema(required = true)
    private String area;

    @Null
    @JsonSchema(required = true)
    private String settlement;

    @Null
    @JsonSchema(required = true)
    private Double lat;

    @Null
    @JsonSchema(required = true)
    private Double lon;

    @Null
    @JsonProperty("city_kladr_id")
    private Object cityKladrId;

    @Null
    @JsonProperty("street_kladr_id")
    private Object streetKladrId;

    @Null
    @JsonProperty("user_id")
    private Object userId;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("door_phone")
    private String doorPhone;

    @Null
    @JsonSchema(required = true)
    private Object kind;

    @JsonSchema(required = true)
    @JsonProperty("delivery_to_door")
    private Boolean deliveryToDoor;
}
