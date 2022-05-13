package ru.instamart.api.response.eta;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ServiceParametersEtaResponse extends BaseResponseObject {

    @JsonSchema(required = true)
    private Integer avgPositionsPerPlace;
    @JsonSchema(required = true)
    private Integer collectionSpeedSecPerPos;
    @JsonSchema(required = true)
    private Integer courierSpeed;
    @JsonSchema(required = true)
    private Integer courierSpeedDelivery;
    @JsonSchema(required = true)
    private Double curveFactorDelivery;
    @JsonSchema(required = true)
    private Integer deliveryTimeSigma;
    @JsonSchema(required = true)
    private Boolean isMlEnabled;
    @JsonSchema(required = true)
    private Boolean isSigmaEnabled;
    @JsonSchema(required = true)
    private Integer onDemandClosingDelta;
    @JsonSchema(required = true)
    private Double routeEstimatorTimeout;
    @JsonSchema(required = true)
    private String storeClosingTime;
    @JsonSchema(required = true)
    private String storeOpeningTime;
    @JsonSchema(required = true)
    private Integer toPlaceSec;
    @JsonSchema(required = true)
    private Double waitMlTimeout;
    @JsonSchema(required = true)
    private Integer window;
}
