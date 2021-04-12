package ru.instamart.api.objects.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderV3 extends BaseResponseObject {
    private String id;
    private String number;
    @JsonProperty("retailer_id")
    private String retailerId;
    @JsonProperty("opaque_packages")
    private List<Object> opaquePackages = null;
    private Integer total;
    private List<Object> features = null;
    @JsonProperty("item_total")
    private Integer itemTotal;
    @JsonProperty("ship_total")
    private Integer shipTotal;
    @JsonProperty("item_count")
    private Integer itemCount;
    @JsonProperty("promo_total")
    private Integer promoTotal;
    @JsonProperty("shipment_state")
    private String shipmentState;
    @JsonProperty("payment_state")
    private String paymentState;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    private String state;
    private String comment;
    private ContactV3 contact;
    @JsonProperty("shipping_crew")
    private List<Object> shippingCrew = null;
    private List<PackageV3> packages = null;
    private List<PaymentV3> payments = null;
    private ShippingV3 shipping;
}
