package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class EquipmentV1 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private Integer shopperId;

    @JsonSchema(required = true)
    private String serial;

    @JsonSchema(required = true)
    private String suppliedAt;

    @Null
    @JsonSchema(required = true)
    private String kind;
}
