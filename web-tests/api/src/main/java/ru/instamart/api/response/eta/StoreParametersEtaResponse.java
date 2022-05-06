package ru.instamart.api.response.eta;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoreParametersEtaResponse extends BaseResponseObject {
    @JsonSchema(required = true)
    private String id;
    @JsonSchema(required = true)
    private String retailerId;
    @JsonSchema(required = true)
    private double lat;
    @JsonSchema(required = true)
    private double lon;
    @JsonSchema(required = true)
    private String timezone;
    @JsonSchema(required = true)
    private boolean isMLEnabled;
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
    private boolean isSigmaEnabled;
}
