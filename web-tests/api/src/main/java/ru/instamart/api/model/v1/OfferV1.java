package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper = false)
public class OfferV1 extends BaseObject {
    @JsonSchema(required = true)
    private Boolean active;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private String uuid;

    @EqualsAndHashCode.Exclude
    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private Double price;

    @JsonSchema(required = true)
    private Double discount;

    @JsonSchema(required = true)
    private Boolean discounted;

    @EqualsAndHashCode.Exclude
    @JsonSchema(required = true)
    @JsonProperty(value = "instamart_price")
    private Double instamartPrice;

    @JsonSchema(required = true)
    @JsonProperty(value = "items_per_pack")
    private Integer itemsPerPack;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "product_id")
    private Long productId;

    @JsonSchema(required = true)
    @JsonProperty(value = "retailer_id")
    private Integer retailerId;

    @JsonSchema(required = true)
    @JsonProperty(value = "store_id")
    private Integer storeId;

    @JsonSchema(required = true)
    @JsonProperty(value = "retailer_sku")
    private String retailerSku;

    @Null
    @JsonSchema(required = true)
    private String rsku;

    @JsonSchema(required = true)
    @JsonProperty(value = "stock_rate")
    private Integer stockRate;

    @EqualsAndHashCode.Exclude
    @JsonProperty(value = "stock_rate_description")
    private String stockRateDescription;

    @JsonSchema(required = true)
    @JsonProperty(value = "max_stock_rate")
    private Integer maxStockRate;

    @Null
    @EqualsAndHashCode.Exclude
    @JsonSchema(required = true)
    @JsonProperty(value = "discount_ends_at")
    private String discountEndsAt;

    @EqualsAndHashCode.Exclude
    private Boolean published;

    @EqualsAndHashCode.Exclude
    @JsonProperty(value = "product_name")
    private String productName;

    @EqualsAndHashCode.Exclude
    @JsonProperty(value = "pickup_order")
    private Integer pickupOrder;

    @Null
    @EqualsAndHashCode.Exclude
    @JsonProperty(value = "product_sku")
    private String productSku;

    @EqualsAndHashCode.Exclude
    @JsonProperty(value = "retailer_price")
    private Double retailerPrice;

    @EqualsAndHashCode.Exclude
    @JsonProperty(value = "cost_price")
    private Double costPrice;

    @EqualsAndHashCode.Exclude
    @JsonSchema(required = true)
    @JsonProperty(value = "price_type")
    private String priceType;

    @EqualsAndHashCode.Exclude
    @JsonProperty(value = "offer_price")
    private Double offerPrice;

    @JsonSchema(required = true)
    @JsonProperty(value = "unit_price")
    private Double unitPrice;

    @Null
    @EqualsAndHashCode.Exclude
    @JsonSchema(required = true)
    @JsonProperty(value = "grams_per_unit")
    private Integer gramsPerUnit;

    @Null
    @EqualsAndHashCode.Exclude
    @JsonProperty(value = "shelf_life")
    private String shelfLife;

    @EqualsAndHashCode.Exclude
    @JsonSchema(required = true)
    private Integer stock;

    @EqualsAndHashCode.Exclude
    @JsonProperty(value = "max_stock")
    private Integer maxStock;

    @EqualsAndHashCode.Exclude
    @JsonProperty(value = "vat_rate")
    private Integer vatRate;

    @EqualsAndHashCode.Exclude
    @JsonProperty(value = "updated_at")
    private String updatedAt;

    @Null
    @JsonSchema(required = true)
    private String permalink;

    @EqualsAndHashCode.Exclude
    private StoresV1 store;

    @Null
    @EqualsAndHashCode.Exclude
    private VariantV1 variant;

    @Null
    @EqualsAndHashCode.Exclude
    @JsonProperty(value = "master_category")
    private MasterCategoryV1 masterCategory;

    @EqualsAndHashCode.Exclude
    @JsonProperty(value = "original_unit_price")
    private Double originalUnitPrice;

    @EqualsAndHashCode.Exclude
    @JsonProperty(value = "vat_info")
    private Object vatInfo;

    @Override
    public String toString() {
        return "store: " + store +
                ";\nname: " + name +
                ";\nactive: " + active;
    }
}
