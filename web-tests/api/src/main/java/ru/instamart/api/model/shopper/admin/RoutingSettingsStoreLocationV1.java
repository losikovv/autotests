package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoutingSettingsStoreLocationV1 extends BaseObject {
    @JsonSchema(required = true)
    @Null
    private String fullName;

    @JsonSchema(required = true)
    @Null
    private String phone;

    @JsonSchema(required = true)
    @Null
    private String address1;

    @JsonSchema(required = true)
    @Null
    private String comments;

    @JsonSchema(required = true)
    @Null
    private String entrance;

    @JsonSchema(required = true)
    @Null
    private String doorPhone;

    @JsonSchema(required = true)
    @Null
    private String vatsPhone;

    @JsonSchema(required = true)
    @Null
    private String floor;

    @JsonSchema(required = true)
    private Float lat;

    @JsonSchema(required = true)
    private Float lon;

    @JsonSchema(required = true)
    private Boolean deliveryToDoor;

    @JsonSchema(required = true)
    private String fullAddress;

    @JsonSchema(required = true)
    private String city;

    @JsonSchema(required = true)
    private String street;

    @JsonSchema(required = true)
    private String building;
}
