package ru.instamart.api.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddressV3 extends BaseObject {
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
    private String entrance;

    @Null
    @JsonSchema(required = true)
    private String floor;

    @Null
    @JsonSchema(required = true)
    private String apartment;

    @Null
    @JsonSchema(required = true)
    private String comments;

    @JsonSchema(required = true)
    private Double lat;

    @JsonSchema(required = true)
    private Double lon;

    @JsonSchema(required = true)
    private String kind;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("door_phone")
    private String doorPhone;

    @JsonSchema(required = true)
    @JsonProperty("delivery_to_door")
    private Boolean deliveryToDoor;
}
