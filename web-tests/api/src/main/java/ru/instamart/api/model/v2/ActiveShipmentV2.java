
package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ActiveShipmentV2 extends BaseObject {

    @Null
    private Object delay;

    @JsonSchema(required = true)
    @JsonProperty("delivery_window")
    private DeliveryWindowV2 deliveryWindow;

    @JsonSchema(required = true)
    private Integer id;

    @NotEmpty
    @JsonSchema(required = true)
    @JsonProperty("line_items")
    private List<LineItemV2> lineItems;

    @JsonSchema(required = true)
    private MergeV2 merge;

    @JsonSchema(required = true)
    private String number;

    @JsonSchema(ignore = true)
    private OrderV2 order;

    @JsonSchema(required = true)
    private RetailerV2 retailer;

    @JsonSchema(required = true)
    private String state;

    @JsonSchema(required = true)
    @JsonProperty("store_id")
    private Integer storeId;
}
