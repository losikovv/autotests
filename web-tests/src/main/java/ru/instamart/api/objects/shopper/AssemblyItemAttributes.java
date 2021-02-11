package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

import java.util.List;

public class AssemblyItemAttributes extends BaseObject {

    private Integer assemblyId;
    private Object issue;
    private Object issueMessage;
    private String packs;
    private Integer itemsPerPack;
    private Integer quantity;
    private Integer foundQuantity;
    private Double size;
    private String humanSize;
    private Double weight;
    private Object thumbnail;
    private String price;
    private String amount;
    private String retailerPrice;
    private String retailerAmount;
    private String humanVolume;
    private Integer pickupOrder;
    private String name;
    private String retailerName;
    private String retailerSku;
    private String productSku;
    private String state;
    private Double totalWeight;
    private String pricerType;
    private Integer qty;
    private Object foundQty;
    private List<Object> foundQtys = null;
    private Integer originalQuantity;
    private String pcs;
    private String productionDate;
    private String bestBefore;
    private Integer vatRate;
    private String unit;
    private String priceWithDiscount;
    private String updatedAt;
    private List<String> eans = null;
    private Boolean isTouched;
    private Boolean isMarked;
    private Object cancelReason;
    private Object replacement;
    private Object fromItemId;
    private Object additionalItemForReplacementId;
    private Object clarificationReason;

    public Integer getAssemblyId() {
        return assemblyId;
    }

    public void setAssemblyId(Integer assemblyId) {
        this.assemblyId = assemblyId;
    }

    public Object getIssue() {
        return issue;
    }

    public void setIssue(Object issue) {
        this.issue = issue;
    }

    public Object getIssueMessage() {
        return issueMessage;
    }

    public void setIssueMessage(Object issueMessage) {
        this.issueMessage = issueMessage;
    }

    public String getPacks() {
        return packs;
    }

    public void setPacks(String packs) {
        this.packs = packs;
    }

    public Integer getItemsPerPack() {
        return itemsPerPack;
    }

    public void setItemsPerPack(Integer itemsPerPack) {
        this.itemsPerPack = itemsPerPack;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getFoundQuantity() {
        return foundQuantity;
    }

    public void setFoundQuantity(Integer foundQuantity) {
        this.foundQuantity = foundQuantity;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getHumanSize() {
        return humanSize;
    }

    public void setHumanSize(String humanSize) {
        this.humanSize = humanSize;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Object getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Object thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRetailerPrice() {
        return retailerPrice;
    }

    public void setRetailerPrice(String retailerPrice) {
        this.retailerPrice = retailerPrice;
    }

    public String getRetailerAmount() {
        return retailerAmount;
    }

    public void setRetailerAmount(String retailerAmount) {
        this.retailerAmount = retailerAmount;
    }

    public String getHumanVolume() {
        return humanVolume;
    }

    public void setHumanVolume(String humanVolume) {
        this.humanVolume = humanVolume;
    }

    public Integer getPickupOrder() {
        return pickupOrder;
    }

    public void setPickupOrder(Integer pickupOrder) {
        this.pickupOrder = pickupOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public String getRetailerSku() {
        return retailerSku;
    }

    public void setRetailerSku(String retailerSku) {
        this.retailerSku = retailerSku;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getPricerType() {
        return pricerType;
    }

    public void setPricerType(String pricerType) {
        this.pricerType = pricerType;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Object getFoundQty() {
        return foundQty;
    }

    public void setFoundQty(Object foundQty) {
        this.foundQty = foundQty;
    }

    public List<Object> getFoundQtys() {
        return foundQtys;
    }

    public void setFoundQtys(List<Object> foundQtys) {
        this.foundQtys = foundQtys;
    }

    public Integer getOriginalQuantity() {
        return originalQuantity;
    }

    public void setOriginalQuantity(Integer originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    public String getPcs() {
        return pcs;
    }

    public void setPcs(String pcs) {
        this.pcs = pcs;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(String bestBefore) {
        this.bestBefore = bestBefore;
    }

    public Integer getVatRate() {
        return vatRate;
    }

    public void setVatRate(Integer vatRate) {
        this.vatRate = vatRate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(String priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getEans() {
        return eans;
    }

    public void setEans(List<String> eans) {
        this.eans = eans;
    }

    public Boolean getIsTouched() {
        return isTouched;
    }

    public void setIsTouched(Boolean isTouched) {
        this.isTouched = isTouched;
    }

    public Boolean getIsMarked() {
        return isMarked;
    }

    public void setIsMarked(Boolean isMarked) {
        this.isMarked = isMarked;
    }

    public Object getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(Object cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Object getReplacement() {
        return replacement;
    }

    public void setReplacement(Object replacement) {
        this.replacement = replacement;
    }

    public Object getFromItemId() {
        return fromItemId;
    }

    public void setFromItemId(Object fromItemId) {
        this.fromItemId = fromItemId;
    }

    public Object getAdditionalItemForReplacementId() {
        return additionalItemForReplacementId;
    }

    public void setAdditionalItemForReplacementId(Object additionalItemForReplacementId) {
        this.additionalItemForReplacementId = additionalItemForReplacementId;
    }

    public Object getClarificationReason() {
        return clarificationReason;
    }

    public void setClarificationReason(Object clarificationReason) {
        this.clarificationReason = clarificationReason;
    }

}
