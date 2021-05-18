package ru.instamart.api.model.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssemblyAttributesSHP extends BaseObject {
    private Integer shipmentId;
    private String state;
    private Double weight;
    private String logisticVolume;
    private String total;
    private String retailerTotal;
    private OriginalShippingTotalsSHP originalShippingTotals;
    private Object shippedAt;
    private List<Object> trolleyNumbers = null;
    private Object packerId;
    private String collectingStartedAt;
    private Object purchasedAt;
    private Integer timeForCollecting;
    private String shipmentNumber;
}
