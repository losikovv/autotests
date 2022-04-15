
package ru.instamart.api.model.workflows;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class Shipment extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("is_heavy")
    private Boolean isHeavy;

    @JsonSchema(required = true)
    @JsonProperty("is_new")
    private Boolean isNew;

    @JsonSchema(required = true)
    @JsonProperty("items_count")
    private Integer itemsCount;

    @JsonSchema(required = true)
    @JsonProperty("items_total_amount")
    private Double itemsTotalAmount;

    @JsonSchema(required = true)
    private String number;

    @JsonSchema(required = true)
    @JsonProperty("store_address")
    private String storeAddress;

    @JsonSchema(required = true)
    @JsonProperty("store_name")
    private String storeName;

    @JsonSchema(required = true)
    @JsonProperty("store_uuid")
    private String storeUuid;

    @JsonSchema(required = true)
    private String uuid;

    @JsonSchema(required = true)
    @JsonProperty("weight_kg")
    private Integer weightKg;
}
