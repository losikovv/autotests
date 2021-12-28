package ru.instamart.api.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.*;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductDataV3 extends BaseObject {
    @JsonSchema(required = true)
    private String id;

    @JsonSchema(required = true)
    @JsonProperty("legacy_offer_id")
    private Long legacyOfferId;

    @JsonSchema(required = true)
    @JsonProperty("legacy_product_id")
    private Long legacyProductId;

    @JsonSchema(required = true)
    private String sku;

    @JsonSchema(required = true)
    @JsonProperty(value = "retailer_sku")
    private String retailerSku;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private Double price;

    @JsonSchema(required = true)
    private String slug;

    @JsonSchema(required = true)
    @JsonProperty(value = "original_price")
    private Double originalPrice;

    @JsonSchema(required = true)
    private Double discount;

    @JsonSchema(required = true)
    @JsonProperty(value = "human_volume")
    private String humanVolume;

    @JsonSchema(required = true)
    private Double volume;

    @JsonSchema(required = true)
    @JsonProperty(value = "volume_type")
    private String volumeType;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "discount_ends_at")
    private String discountEndsAt;

    @JsonSchema(required = true)
    @JsonProperty(value = "items_per_pack")
    private Integer itemsPerPack;

    @JsonSchema(required = true)
    @JsonProperty(value = "max_select_quantity")
    private Integer maxSelectQuantity;

    @EqualsAndHashCode.Exclude
    @JsonSchema(required = true)
    private List<RequirementV2> requirements = null;

    @EqualsAndHashCode.Exclude
    @JsonSchema(required = true)
    @JsonProperty(value = "image_urls")
    private List<String> imageUrls = null;

    @EqualsAndHashCode.Exclude
    @JsonSchema(required = true)
    @JsonProperty(value = "promo_badge_ids")
    private List<Object> promoBadgeIds = null;

    @JsonSchema(required = true)
    @JsonProperty(value = "price_type")
    private String priceType;

    @JsonSchema(required = true)
    @JsonProperty(value = "grams_per_unit")
    private Double gramsPerUnit;

    @JsonSchema(required = true)
    @JsonProperty(value = "unit_price")
    private Double unitPrice;

    @JsonSchema(required = true)
    @JsonProperty(value = "original_unit_price")
    private Double originalUnitPrice;

    @JsonSchema(required = true)
    private Boolean available;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "vat_info")
    private Object vatInfo;

    @JsonSchema(required = true)
    @JsonProperty(value = "canonical_url")
    private String canonicalUrl;
}
