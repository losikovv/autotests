
package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OfferV2 extends BaseObject {

    @JsonSchema(required = true)
    private Boolean active;

    @Null
    private Double discount;

    @Null
    @JsonProperty("discount_ends_at")
    private Object discountEndsAt;

    @JsonSchema(required = true)
    @JsonProperty("grams_per_unit")
    private Integer gramsPerUnit;

    @JsonSchema(required = true)
    @JsonProperty("human_volume")
    private String humanVolume;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private List<ImageV2> images;

    @JsonSchema(required = true)
    @JsonProperty("items_per_pack")
    private Integer itemsPerPack;

    @JsonSchema(required = true)
    private List<Object> labels;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    @JsonProperty("original_price")
    private Double originalPrice;

    @JsonSchema(required = true)
    @JsonProperty("original_unit_price")
    private Double originalUnitPrice;

    @JsonSchema(required = true)
    private Double price;

    @JsonSchema(required = true)
    @JsonProperty("price_type")
    private String priceType;

    @JsonSchema(required = true)
    @JsonProperty("promo_badge_ids")
    private List<Object> promoBadgeIds;

    @JsonSchema(required = true)
    private List<Object> requirements;

    @JsonSchema(required = true)
    @JsonProperty("retailer_sku")
    private String retailerSku;

    @Null
    private Object score;

    @JsonProperty("score_details")
    private ScoreDetailsV2 scoreDetails;

    @JsonSchema(required = true)
    private String sku;

    @JsonSchema(required = true)
    @JsonProperty("unit_price")
    private Double unitPrice;

    @JsonSchema(required = true)
    private Double volume;

    @JsonSchema(required = true)
    @JsonProperty("volume_type")
    private String volumeType;
}
