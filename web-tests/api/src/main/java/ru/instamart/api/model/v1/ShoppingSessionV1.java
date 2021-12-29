
package ru.instamart.api.model.v1;

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
public class ShoppingSessionV1 extends BaseObject {
    @NotEmpty
    @JsonProperty("available_stores")
    private List<StoreV1> availableStores;

    @Null
    @JsonProperty("current_ship_address")
    private Object currentShipAddress;

    @Null
    @JsonProperty("current_user")
    private UserV1 currentUser;

    @JsonSchema(required = true)
    @JsonProperty("favorite_products_skus")
    private List<String> favoriteProductsSkus;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("shipping_method_kind")
    private String shippingMethodKind;

    @Null
    @JsonSchema(required = true)
    private StoreV1 store;

    @JsonSchema(required = true)
    @JsonProperty("store_labels")
    private List<Object> storeLabels;
}
