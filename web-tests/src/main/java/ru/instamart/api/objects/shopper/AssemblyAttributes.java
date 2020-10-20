package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

import java.util.List;

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

    public Integer getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Integer shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getLogisticVolume() {
        return logisticVolume;
    }

    public void setLogisticVolume(String logisticVolume) {
        this.logisticVolume = logisticVolume;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getRetailerTotal() {
        return retailerTotal;
    }

    public void setRetailerTotal(String retailerTotal) {
        this.retailerTotal = retailerTotal;
    }

    public OriginalShippingTotals getOriginalShippingTotals() {
        return originalShippingTotals;
    }

    public void setOriginalShippingTotals(OriginalShippingTotals originalShippingTotals) {
        this.originalShippingTotals = originalShippingTotals;
    }

    public Object getShippedAt() {
        return shippedAt;
    }

    public void setShippedAt(Object shippedAt) {
        this.shippedAt = shippedAt;
    }

    public List<Object> getTrolleyNumbers() {
        return trolleyNumbers;
    }

    public void setTrolleyNumbers(List<Object> trolleyNumbers) {
        this.trolleyNumbers = trolleyNumbers;
    }

    public Object getPackerId() {
        return packerId;
    }

    public void setPackerId(Object packerId) {
        this.packerId = packerId;
    }

    public String getCollectingStartedAt() {
        return collectingStartedAt;
    }

    public void setCollectingStartedAt(String collectingStartedAt) {
        this.collectingStartedAt = collectingStartedAt;
    }

    public Object getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt(Object purchasedAt) {
        this.purchasedAt = purchasedAt;
    }

    public Integer getTimeForCollecting() {
        return timeForCollecting;
    }

    public void setTimeForCollecting(Integer timeForCollecting) {
        this.timeForCollecting = timeForCollecting;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

}
