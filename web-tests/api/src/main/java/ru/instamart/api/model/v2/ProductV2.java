package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductV2 extends BaseObject {
    private Long id;
    private String sku;
    @JsonProperty(value = "retailer_sku")
    private String retailerSku;
    private String name;
    private Double price;
    @JsonProperty(value = "original_price")
    private Double originalPrice;
    private Double discount;
    @JsonProperty(value = "human_volume")
    private String humanVolume;
    private Double volume;
    @JsonProperty(value = "volume_type")
    private String volumeType;
    @JsonProperty(value = "discount_ends_at")
    private String discountEndsAt;
    @JsonProperty(value = "items_per_pack")
    private Integer itemsPerPack;
    private String description;
    private List<ImageV2> images = null;
    private ImageV2 image;
    private List<PropertyV2> properties = null;
    @JsonProperty(value = "related_products")
    private List<Object> relatedProducts = null;
    private List<Object> requirements = null;
    @JsonProperty(value = "price_type")
    private String priceType;
    @JsonProperty(value = "grams_per_unit")
    private Double gramsPerUnit;
    @JsonProperty(value = "unit_price")
    private Double unitPrice;
    @JsonProperty(value = "original_unit_price")
    private Double originalUnitPrice;
}
