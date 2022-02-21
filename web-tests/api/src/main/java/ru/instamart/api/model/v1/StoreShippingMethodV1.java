
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.ShippingMethodV2;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoreShippingMethodV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("available_on")
    private String availableOn;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    @JsonProperty("shipping_method")
    private ShippingMethodV2 shippingMethod;

    @JsonSchema(required = true)
    @JsonProperty("store_id")
    private Integer storeId;

    @JsonSchema(required = true)
    @JsonProperty("tenant_id")
    private String tenantId;

}
