
package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;


@Data
@EqualsAndHashCode(callSuper=false)
public class AssemblyItemV2 extends BaseObject {

    @JsonProperty("found_pcs")
    private Integer foundPcs;
    @JsonProperty("found_quantity")
    private String foundQuantity;
    @JsonProperty("human_volume")
    private String humanVolume;
    private ImageV2 image;
    @JsonProperty("items_per_pack")
    private Integer itemsPerPack;
    private String name;
    @JsonProperty("new_price")
    private Double newPrice;
    @JsonProperty("new_unit_price")
    private Double newUnitPrice;
    private Integer pcs;
    private Double price;
    @JsonProperty("price_type")
    private String priceType;
    @JsonProperty("product_id")
    private Long productId;
    @JsonProperty("product_in_stock")
    private Boolean productInStock;
    private String quantity;
    private ReplacementV2 replacement;
    private String sku;
    private String state;
    private Double total;
    @JsonProperty("unit_price")
    private Double unitPrice;
}
