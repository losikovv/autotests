package ru.instamart.api.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShippingV3 extends BaseObject {
    private String type;
    @JsonProperty("option_id")
    private String optionId;
    @JsonProperty("store_id")
    private String storeId;
    private Integer price;
    @JsonProperty("original_price")
    private Integer originalPrice;
    @JsonProperty("shipped_at")
    private String shippedAt;
    private String state;
    private AddressV3 address;
    @JsonProperty("delivery_window")
    private DeliveryWindowV3 deliveryWindow;
    private List<RateV3> rates = null;
}
