package instamart.api.objects.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class LineItem extends BaseObject {
    @JsonProperty(value = "human_volume")
    private String humanVolume;
    private Integer id;
    private String image;
    private String name;
    @JsonProperty(value = "offer_id")
    private Integer offerId;
    @JsonProperty(value = "product_permalink")
    private String productPermalink;
    private Integer quantity;
    @JsonProperty(value = "retailer_id")
    private Integer retailerId;
    @JsonProperty(value = "shipment_id")
    private Integer shipmentId;
    @JsonProperty(value = "shipment_number")
    private String shipmentNumber;
    @JsonProperty(value = "small_image")
    private String smallImage;
    private String sku;
    @JsonProperty(value = "assembly_issue")
    private Object assemblyIssue;
    @JsonProperty(value = "items_per_pack")
    private Integer itemsPerPack;
    private Integer packs;
    private Double price;
    @JsonProperty(value = "price_type")
    private String priceType;
    private Double total;
    @JsonProperty(value = "unit_price")
    private Double unitPrice;
    @JsonProperty(value = "unit_quantity")
    private Double unitQuantity;
    @JsonProperty(value = "updated_at")
    private String updatedAt;
    private String uuid;
    @JsonProperty(value = "variant_id")
    private Integer variantId;
    @JsonProperty(value = "vat_rate")
    private Integer vatRate;
    private Offer offer;
    @JsonProperty(value = "master_category")
    private Object masterCategory;
}
