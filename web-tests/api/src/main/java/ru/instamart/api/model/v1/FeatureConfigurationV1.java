
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class FeatureConfigurationV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("brands__is_new_promo_slider_visible")
    private Boolean brandsIsNewPromoSliderVisible;

    @JsonSchema(required = true)
    @JsonProperty("brands__is_own_ml_cart_recommendations_enabled")
    private Boolean brandsIsOwnMlCartRecommendationsEnabled;

    @JsonSchema(required = true)
    @JsonProperty("show_delivery_price_for_new_retailer_cards")
    private Boolean showDeliveryPriceForNewRetailerCards;
}
