package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class OfferV1 extends BaseObject {
    private Boolean active;
    private Long id;
    private String uuid;
    private String name;
    private Double price;
    private Double discount;
    private Boolean discounted;
    @JsonProperty(value = "instamart_price")
    private Double instamartPrice;
    @JsonProperty(value = "items_per_pack")
    private Integer itemsPerPack;
    @JsonProperty(value = "product_id")
    private Long productId;
    @JsonProperty(value = "retailer_id")
    private Integer retailerId;
    @JsonProperty(value = "store_id")
    private Integer storeId;
    @JsonProperty(value = "retailer_sku")
    private String retailerSku;
    private String rsku;
    @JsonProperty(value = "stock_rate")
    private Integer stockRate;
    @JsonProperty(value = "stock_rate_description")
    private String stockRateDescription;
    @JsonProperty(value = "max_stock_rate")
    private Integer maxStockRate;
    @JsonProperty(value = "discount_ends_at")
    private String discountEndsAt;
    private Boolean published;
    @JsonProperty(value = "product_name")
    private String productName;
    @JsonProperty(value = "pickup_order")
    private Integer pickupOrder;
    @JsonProperty(value = "product_sku")
    private String productSku;
    @JsonProperty(value = "retailer_price")
    private Double retailerPrice;
    @JsonProperty(value = "cost_price")
    private Double costPrice;
    @JsonProperty(value = "price_type")
    private String priceType;
    @JsonProperty(value = "offer_price")
    private Double offerPrice;
    @JsonProperty(value = "unit_price")
    private Double unitPrice;
    @JsonProperty(value = "grams_per_unit")
    private Integer gramsPerUnit;
    @JsonProperty(value = "shelf_life")
    private String shelfLife;
    private Integer stock;
    @JsonProperty(value = "max_stock")
    private Integer maxStock;
    @JsonProperty(value = "vat_rate")
    private Integer vatRate;
    @JsonProperty(value = "updated_at")
    private String updatedAt;
    private String permalink;
    private StoreV1 store;
    private VariantV1 variant;
    @JsonProperty(value = "master_category")
    private MasterCategoryV1 masterCategory;
    @JsonProperty(value = "original_unit_price")
    private Double originalUnitPrice;
    @JsonProperty(value = "vat_info")
    private Object vatInfo;

    @Override
    public String toString() {
        return "store: " + store +
                ";\nname: " + name +
                ";\nactive: " + active;
    }
}
