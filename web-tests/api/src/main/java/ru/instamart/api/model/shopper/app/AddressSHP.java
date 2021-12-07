package ru.instamart.api.model.shopper.app;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddressSHP extends BaseObject {
    @Null
    @JsonSchema(required = true)
    private String fullName;

    @Null
    @JsonSchema(required = true)
    private String phone;

    @JsonSchema(required = true)
    private String address1;

    @Null
    @JsonSchema(required = true)
    private String comments;

    @Null
    @JsonSchema(required = true)
    private String entrance;

    @Null
    @JsonSchema(required = true)
    private String doorPhone;

    @Null
    @JsonSchema(required = true)
    private String vatsPhone;
    @Null
    @JsonSchema(required = true)
    private String floor;

    @Null
    @JsonSchema(required = true)
    private Double lat;

    @Null
    @JsonSchema(required = true)
    private Double lon;

    @Null
    @JsonSchema(required = true)
    private Boolean deliveryToDoor;

    @JsonSchema(required = true)
    private String fullAddress;

    @Null
    @JsonSchema(required = true)
    private String city;

    @Null
    @JsonSchema(required = true)
    private String street;

    @Null
    @JsonSchema(required = true)
    private String building;
}
