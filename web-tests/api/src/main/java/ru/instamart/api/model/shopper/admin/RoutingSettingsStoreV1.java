package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoutingSettingsStoreV1 {
    @JsonSchema(required = true)
    private int id;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private String uuid;

    @JsonSchema(required = true)
    private int operationalZoneId;

    @JsonSchema(required = true)
    private int retailerId;

    @JsonSchema(required = true)
    private Boolean active;

    @JsonSchema(required = true)
    private Boolean hasConveyor;

    @JsonSchema(required = true)
    private Boolean fastPayment;

    @JsonSchema(required = true)
    private Boolean fastPaymentCashless;

    @JsonSchema(required = true)
    @Null
    private String fastPaymentMetroStoreDns;

    @JsonSchema(required = true)
    private Boolean expressDelivery;

    @JsonSchema(required = true)
    @Null
    private String secondsForAssemblyItem;

    @JsonSchema(required = true)
    @Null
    private String additionalSecondsForAssembly;

    @JsonSchema(required = true)
    private Boolean autoRouting;

    @JsonSchema(required = true)
    private Boolean boxScanning;

    @JsonSchema(required = true)
    @Null
    private String deliveryAreaId;

    @JsonSchema(required = true)
    private String scheduleType;

    @JsonSchema(required = true)
    private String scheduleTypeHumanName;

    @JsonSchema(required = true)
    private RoutingSettingsStoreLocationV1 location;

    @JsonSchema(required = true)
    @Null
    private String fastPaymentMetroBarcodeMasked;
}
