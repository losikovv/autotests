package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class OriginalShippingTotals extends BaseObject {
    private Double total;
    private Double weight;
    private Double shipmentCost;
    private Double shipmentAdjustmentTotal;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getShipmentCost() {
        return shipmentCost;
    }

    public void setShipmentCost(Double shipmentCost) {
        this.shipmentCost = shipmentCost;
    }

    public Double getShipmentAdjustmentTotal() {
        return shipmentAdjustmentTotal;
    }

    public void setShipmentAdjustmentTotal(Double shipmentAdjustmentTotal) {
        this.shipmentAdjustmentTotal = shipmentAdjustmentTotal;
    }

}
