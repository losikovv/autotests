
package ru.instamart.api.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentV3 extends BaseObject {

    @JsonSchema(required = true)
    private List<Object> alerts;

    @JsonSchema(required = true)
    private Boolean checkout;

    @JsonSchema(required = true)
    private Double cost;

    @JsonSchema(required = true)
    @JsonProperty("delivery_window")
    private Object deliveryWindow;

    @JsonSchema(required = true)
    private Double discount;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    @JsonProperty("is_alcohol")
    private Boolean isAlcohol;

    @JsonSchema(required = true)
    @JsonProperty("item_count")
    private Integer itemCount;

    @JsonSchema(required = true)
    @JsonProperty("item_total")
    private Double itemTotal;

    @JsonSchema(required = true)
    @JsonProperty("items_quantity")
    private Integer itemsQuantity;

    @JsonSchema(required = true)
    private String number;

    @JsonSchema(required = true)
    private Double promo;

    @JsonSchema(required = true)
    private RetailerV3 retailer;

    @JsonSchema(required = true)
    @JsonProperty("shipping_rate")
    private Object shippingRate;

    @JsonSchema(required = true)
    private CheckoutStoreV3 store;

    @JsonSchema(required = true)
    @JsonProperty("total_weight")
    private Integer totalWeight;
}
