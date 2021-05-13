package ru.instamart.api.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class LineItemV3 extends BaseObject {
    @JsonProperty("product_id")
    private String productId;
    private String name;
    private Double quantity;
    private List<Object> features = null;
    private Integer price;
    @JsonProperty("original_price")
    private Integer originalPrice;
    private Integer total;
    @JsonProperty("promo_total")
    private Integer promoTotal;
    private Object state;
    @JsonProperty("size_label")
    private SizeLabelV3 sizeLabel;
    @JsonProperty("discount_ends_at")
    private Object discountEndsAt;
    private List<ModificationV3> modifications = null;
}
