package ru.instamart.api.objects.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class PackageV3 extends BaseObject {
    private String id;
    private String number;
    @JsonProperty("item_count")
    private Integer itemCount;
    private List<Object> features = null;
    private Integer total;
    @JsonProperty("item_total")
    private Integer itemTotal;
    @JsonProperty("ship_total")
    private Integer shipTotal;
    @JsonProperty("promo_total")
    private Integer promoTotal;
    private Double weight;
    @JsonProperty("line_items")
    private List<LineItemV3> lineItems = null;
}
