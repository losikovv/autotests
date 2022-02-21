
package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompleteOrderV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("completed_order_id")
    private Long completedOrderId;

    @JsonSchema(required = true)
    private String notice;

    @JsonSchema(required = true)
    @JsonProperty("redirect_url")
    private String redirectUrl;

    @JsonSchema(required = true)
    @JsonProperty("shipment_number")
    private String shipmentNumber;
}
