package ru.instamart.api.response.eta;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class RetailerParametersEtaResponse extends BaseResponseObject {
    @JsonSchema(required = true)
    private String id;
    @JsonSchema(required = true)
    private int courierSpeed;
    @JsonSchema(required = true)
    private int deliveryTimeSigma;
    @JsonSchema(required = true)
    private int window;
}
