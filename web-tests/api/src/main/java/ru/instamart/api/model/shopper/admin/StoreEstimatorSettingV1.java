package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoreEstimatorSettingV1 extends BaseObject {
    @JsonSchema(required = true)
    private Integer avgParkingMinVehicle;

    @JsonSchema(required = true)
    private Integer averageSpeedForStraightDistanceToClientMin;

    @JsonSchema(required = true)
    private Integer additionalFactorForStraightDistanceToClientMin;

    @JsonSchema(required = true)
    private Integer orderTransferTimeFromAssemblyToDeliveryMin;

    @JsonSchema(required = true)
    private Integer avgToPlaceMinExternal;

    @JsonSchema(required = true)
    private Integer orderTransferTimeFromDeliveryToClientMin;

    @JsonSchema(required = true)
    private Integer orderReceiveTimeFromAssemblyToDeliveryMin;

    @JsonSchema(required = true)
    private String storeUuid;
}
