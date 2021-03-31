package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OriginalShippingTotals extends BaseObject {
    private Double total;
    private Double weight;
    private Double shipmentCost;
    private Double shipmentAdjustmentTotal;
}
