package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

import java.util.List;

public class ShipmentAttributes extends BaseObject {

    private String number;
    private Integer weight;
    private String logisticVolume;
    private String state;
    private String dispatcherComment;
    private Integer itemCount;
    private Boolean isOnlinePayment;
    private String tenantId;
    private String retailer;
    private String uuid;
    private String email;
    private String lifePayPaymentMethod;
    private Boolean isFirstOrder;
    private Boolean closingDocsRequired;
    private Boolean isOutdated;
    private List<Object> linkedShipments = null;
    private String deliveryBefore;
    private String inspectionResult;
    private String total;
    private String cost;
    private String itemTotal;
    private String adjustmentTotal;
    private String deliveryDate;
    private String deliveryInterval;
    private String customerComment;
    private String orderDispatcherComment;
    private Address address;
    private String retailerName;
    private String assemblyState;
    private Object totalGreaterThanReceipt;
    private Payment payment;
    private ReplacementPolicy replacementPolicy;
    private Boolean isPaid;
    private Object canBePrinted;
    private Boolean canChangePaymentTool;
    private String storeImportKey;
    private Object retailerCard;
    private String assemblyDeadlineStatus;
    private String assemblyDeadlineAt;
    private Boolean isPickup;
    private Boolean isAlcohol;
    private Boolean isTerminalState;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getLogisticVolume() {
        return logisticVolume;
    }

    public void setLogisticVolume(String logisticVolume) {
        this.logisticVolume = logisticVolume;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Object getDispatcherComment() {
        return dispatcherComment;
    }

    public void setDispatcherComment(String dispatcherComment) {
        this.dispatcherComment = dispatcherComment;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Boolean getIsOnlinePayment() {
        return isOnlinePayment;
    }

    public void setIsOnlinePayment(Boolean isOnlinePayment) {
        this.isOnlinePayment = isOnlinePayment;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLifePayPaymentMethod() {
        return lifePayPaymentMethod;
    }

    public void setLifePayPaymentMethod(String lifePayPaymentMethod) {
        this.lifePayPaymentMethod = lifePayPaymentMethod;
    }

    public Boolean getIsFirstOrder() {
        return isFirstOrder;
    }

    public void setIsFirstOrder(Boolean isFirstOrder) {
        this.isFirstOrder = isFirstOrder;
    }

    public Boolean getClosingDocsRequired() {
        return closingDocsRequired;
    }

    public void setClosingDocsRequired(Boolean closingDocsRequired) {
        this.closingDocsRequired = closingDocsRequired;
    }

    public Boolean getIsOutdated() {
        return isOutdated;
    }

    public void setIsOutdated(Boolean isOutdated) {
        this.isOutdated = isOutdated;
    }

    public List<Object> getLinkedShipments() {
        return linkedShipments;
    }

    public void setLinkedShipments(List<Object> linkedShipments) {
        this.linkedShipments = linkedShipments;
    }

    public String getDeliveryBefore() {
        return deliveryBefore;
    }

    public void setDeliveryBefore(String deliveryBefore) {
        this.deliveryBefore = deliveryBefore;
    }

    public Object getInspectionResult() {
        return inspectionResult;
    }

    public void setInspectionResult(String inspectionResult) {
        this.inspectionResult = inspectionResult;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(String itemTotal) {
        this.itemTotal = itemTotal;
    }

    public String getAdjustmentTotal() {
        return adjustmentTotal;
    }

    public void setAdjustmentTotal(String adjustmentTotal) {
        this.adjustmentTotal = adjustmentTotal;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryInterval() {
        return deliveryInterval;
    }

    public void setDeliveryInterval(String deliveryInterval) {
        this.deliveryInterval = deliveryInterval;
    }

    public String getCustomerComment() {
        return customerComment;
    }

    public void setCustomerComment(String customerComment) {
        this.customerComment = customerComment;
    }

    public Object getOrderDispatcherComment() {
        return orderDispatcherComment;
    }

    public void setOrderDispatcherComment(String orderDispatcherComment) {
        this.orderDispatcherComment = orderDispatcherComment;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public Object getAssemblyState() {
        return assemblyState;
    }

    public void setAssemblyState(String assemblyState) {
        this.assemblyState = assemblyState;
    }

    public Object getTotalGreaterThanReceipt() {
        return totalGreaterThanReceipt;
    }

    public void setTotalGreaterThanReceipt(Object totalGreaterThanReceipt) {
        this.totalGreaterThanReceipt = totalGreaterThanReceipt;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public ReplacementPolicy getReplacementPolicy() {
        return replacementPolicy;
    }

    public void setReplacementPolicy(ReplacementPolicy replacementPolicy) {
        this.replacementPolicy = replacementPolicy;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Object getCanBePrinted() {
        return canBePrinted;
    }

    public void setCanBePrinted(Object canBePrinted) {
        this.canBePrinted = canBePrinted;
    }

    public Boolean getCanChangePaymentTool() {
        return canChangePaymentTool;
    }

    public void setCanChangePaymentTool(Boolean canChangePaymentTool) {
        this.canChangePaymentTool = canChangePaymentTool;
    }

    public String getStoreImportKey() {
        return storeImportKey;
    }

    public void setStoreImportKey(String storeImportKey) {
        this.storeImportKey = storeImportKey;
    }

    public Object getRetailerCard() {
        return retailerCard;
    }

    public void setRetailerCard(Object retailerCard) {
        this.retailerCard = retailerCard;
    }

    public String getAssemblyDeadlineStatus() {
        return assemblyDeadlineStatus;
    }

    public void setAssemblyDeadlineStatus(String assemblyDeadlineStatus) {
        this.assemblyDeadlineStatus = assemblyDeadlineStatus;
    }

    public String getAssemblyDeadlineAt() {
        return assemblyDeadlineAt;
    }

    public void setAssemblyDeadlineAt(String assemblyDeadlineAt) {
        this.assemblyDeadlineAt = assemblyDeadlineAt;
    }

    public Boolean getIsPickup() {
        return isPickup;
    }

    public void setIsPickup(Boolean isPickup) {
        this.isPickup = isPickup;
    }

    public Boolean getIsAlcohol() {
        return isAlcohol;
    }

    public void setIsAlcohol(Boolean isAlcohol) {
        this.isAlcohol = isAlcohol;
    }

    public Boolean getIsTerminalState() {
        return isTerminalState;
    }

    public void setIsTerminalState(Boolean isTerminalState) {
        this.isTerminalState = isTerminalState;
    }

}
