package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class LineItemV1 extends BaseObject {
    @JsonSchema(required = true)
    @JsonProperty(value = "human_volume")
    private String humanVolume;

    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private String image;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    @JsonProperty(value = "offer_id")
    private Integer offerId;

    @JsonSchema(required = true)
    @JsonProperty(value = "product_permalink")
    private String productPermalink;

    @JsonSchema(required = true)
    private Integer quantity;

    @JsonSchema(required = true)
    @JsonProperty(value = "retailer_id")
    private Integer retailerId;

    @JsonSchema(required = true)
    @JsonProperty(value = "shipment_id")
    private Integer shipmentId;

    @JsonSchema(required = true)
    @JsonProperty(value = "shipment_number")
    private String shipmentNumber;

    @JsonSchema(required = true)
    @JsonProperty(value = "small_image")
    private String smallImage;

    @JsonSchema(required = true)
    private String sku;

    @JsonSchema(required = true)
    @JsonProperty(value = "assembly_issue")
    private Object assemblyIssue;

    @JsonSchema(required = true)
    @JsonProperty(value = "items_per_pack")
    private Integer itemsPerPack;

    @JsonSchema(required = true)
    private Integer packs;

    @JsonSchema(required = true)
    private Double price;

    @JsonSchema(required = true)
    @JsonProperty(value = "price_type")
    private String priceType;

    @JsonSchema(required = true)
    private Double total;

    @JsonSchema(required = true)
    @JsonProperty(value = "unit_price")
    private Double unitPrice;

    @JsonSchema(required = true)
    @JsonProperty(value = "unit_quantity")
    private Double unitQuantity;

    @JsonSchema(required = true)
    @JsonProperty(value = "updated_at")
    private String updatedAt;

    @JsonSchema(required = true)
    private String uuid;

    @JsonSchema(required = true)
    @JsonProperty(value = "variant_id")
    private Integer variantId;

    @JsonSchema(required = true)
    @JsonProperty(value = "vat_rate")
    private Integer vatRate;

    @JsonSchema(required = true)
    private OfferV1 offer;

    @JsonSchema(required = true)
    @JsonProperty(value = "master_category")
    private Object masterCategory;
}
