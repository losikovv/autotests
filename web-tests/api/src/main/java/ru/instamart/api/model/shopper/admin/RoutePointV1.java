package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class RoutePointV1 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @Null
    @JsonSchema(required = true)
    private Object externalNumber;

    @JsonSchema(required = true)
    private Integer position;

    @JsonSchema(required = true)
    private Integer shipmentId;

    @Null
    @JsonSchema(required = true)
    private String planArrivalAt;

    @Null
    @JsonSchema(required = true)
    private String factArrivalAt;

    @Null
    @JsonSchema(required = true)
    private String state;

    @Null
    @JsonSchema(required = true)
    private String humanState;

    @JsonSchema(required = true)
    private String number;

    @Null
    @JsonSchema(required = true)
    private LogisticAttributesV1 logisticAttributes;
}
