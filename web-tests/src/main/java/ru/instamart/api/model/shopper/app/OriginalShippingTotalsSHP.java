package ru.instamart.api.model.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class OriginalShippingTotalsSHP extends BaseObject {
    private Double total;
    private Double weight;
    private Double shipmentCost;
    private Double shipmentAdjustmentTotal;
}
