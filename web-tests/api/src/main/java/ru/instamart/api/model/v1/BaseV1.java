
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class BaseV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("send_shipment_collecting_violation_apologies_sms")
    private Boolean sendShipmentCollectingViolationApologiesSms;

    @JsonSchema(required = true)
    @JsonProperty("send_sms")
    private Boolean sendSms;

    @JsonSchema(required = true)
    @JsonProperty("send_start_shipping_sms")
    private Boolean sendStartShippingSms;
}
