package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoreEstimatorSettingV1 extends BaseObject {
    @JsonSchema(required = true)
    private int avgParkingMinVehicle;

    @JsonSchema(required = true)
    private int averageSpeedForStraightDistanceToClientMin;

    @JsonSchema(required = true)
    private int additionalFactorForStraightDistanceToClientMin;

    @JsonSchema(required = true)
    private int orderTransferTimeFromAssemblyToDeliveryMin;

    @JsonSchema(required = true)
    private int avgToPlaceMinExternal;

    @JsonSchema(required = true)
    private int orderTransferTimeFromDeliveryToClientMin;

    @JsonSchema(required = true)
    private int orderReceiveTimeFromAssemblyToDeliveryMin;

    @JsonSchema(required = true)
    private String storeUuid;
}
