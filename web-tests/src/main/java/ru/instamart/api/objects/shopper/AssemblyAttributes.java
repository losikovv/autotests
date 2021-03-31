package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssemblyAttributes extends BaseObject {
    private Integer shipmentId;
    private String state;
    private Double weight;
    private String logisticVolume;
    private String total;
    private String retailerTotal;
    private OriginalShippingTotals originalShippingTotals;
    private Object shippedAt;
    private List<Object> trolleyNumbers = null;
    private Object packerId;
    private String collectingStartedAt;
    private Object purchasedAt;
    private Integer timeForCollecting;
    private String shipmentNumber;
}
