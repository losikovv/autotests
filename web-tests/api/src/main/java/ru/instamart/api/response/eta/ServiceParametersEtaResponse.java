package ru.instamart.api.response.eta;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ServiceParametersEtaResponse extends BaseResponseObject {
    @JsonSchema(required = true)
    private double waitMLTimeout;
    @JsonSchema(required = true)
    private boolean isMLEnabled;
    @JsonSchema(required = true)
    private boolean isSigmaEnabled;
    @JsonSchema(required = true)
    private int avgPositionsPerPlace;
    @JsonSchema(required = true)
    private int toPlaceSec;
    @JsonSchema(required = true)
    private int collectionSpeedSecPerPos;
    @JsonSchema(required = true)
    private String storeOpeningTime;
    @JsonSchema(required = true)
    private String storeClosingTime;
    @JsonSchema(required = true)
    private int onDemandClosingDelta;
    @JsonSchema(required = true)
    private int courierSpeed;
    @JsonSchema(required = true)
    private int deliveryTimeSigma;
    @JsonSchema(required = true)
    private int window;
    @JsonSchema(required = true)
    private int courierSpeedDelivery;
    @JsonSchema(required = true)
    private double curveFactorDelivery;
    @JsonSchema(required = true)
    private double routeEstimatorTimeout;
}
