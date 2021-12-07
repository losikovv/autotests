package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoreV1 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private String uuid;

    @Null
    @JsonSchema(required = true)
    private Integer operationalZoneId;

    @Null
    @JsonSchema(required = true)
    private Integer retailerId;

    @JsonSchema(required = true)
    private Boolean active;

    @Null
    @JsonSchema(required = true)
    private Boolean hasConveyor;

    @JsonSchema(required = true)
    private Boolean fastPayment;

    @JsonSchema(required = true)
    private Boolean fastPaymentCashless;

    @Null
    @JsonSchema(required = true)
    private String fastPaymentMetroStoreDns;

    @JsonSchema(required = true)
    private Boolean expressDelivery;

    @Null
    @JsonSchema(required = true)
    private Integer secondsForAssemblyItem;

    @Null
    @JsonSchema(required = true)
    private Integer additionalSecondsForAssembly;

    @JsonSchema(required = true)
    private Boolean autoRouting;

    @Null
    @JsonSchema(required = true)
    private Boolean boxScanning;

    @Null
    @JsonSchema(required = true)
    private Integer deliveryAreaId;

    @JsonSchema(required = true)
    private String scheduleType;

    @JsonSchema(required = true)
    private String scheduleTypeHumanName;

    @JsonSchema(required = true)
    private LocationV1 location;

    @JsonSchema(required = true)
    private String fastPaymentMetroBarcodeMasked;
}
