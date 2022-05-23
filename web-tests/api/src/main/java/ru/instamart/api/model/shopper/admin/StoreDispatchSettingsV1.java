package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoreDispatchSettingsV1 extends BaseObject {

    @Null
    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private Integer storeId;

    @JsonSchema(required = true)
    private Integer maxOrderAssignRetryCount;

    @JsonSchema(required = true)
    private Integer avgParkingMinVehicle;

    @JsonSchema(required = true)
    private Integer maxCurrentOrderAssignQueue;

    @JsonSchema(required = true)
    private Integer orderWeightThresholdToAssignToVehicleGramms;

    @JsonSchema(required = true)
    private Integer averageSpeedForStraightDistanceToClientMin;

    @JsonSchema(required = true)
    private Integer additionalFactorForStraightDistanceToClientMin;

    @JsonSchema(required = true)
    private Integer orderTransferTimeFromAssemblyToDeliveryMin;

    @JsonSchema(required = true)
    private Integer avgToPlaceMin;

    @JsonSchema(required = true)
    private Integer avgToPlaceMinExternal;

    @JsonSchema(required = true)
    private Integer offerSeenTimeoutSec;

    @JsonSchema(required = true)
    private Integer lastPositionExpire;

    @JsonSchema(required = true)
    private boolean taxiDeliveryOnly;

    @JsonSchema(required = true)
    private boolean placeLocationCenter;

    @JsonSchema(required = true)
    private Integer orderTransferTimeFromDeliveryToClientMin;

    @JsonSchema(required = true)
    private Integer orderReceiveTimeFromAssemblyToDeliveryMin;

    @JsonSchema(required = true)
    private Integer offerServerTimeoutSec;

    @JsonSchema(required = true)
    private boolean externalAssembliersPresented;

    @JsonSchema(required = true)
    private Integer gapTaxiPunishMin;

    @JsonSchema(required = true)
    private boolean taxiAvailable;

}
