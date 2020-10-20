package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

import java.util.List;

public class OfferAttributes extends BaseObject {

    private String uuid;
    private String name;
    private String retailerSku;
    private String productSku;
    private String productName;
    private Double retailerPrice;
    private Integer retailerId;
    private String thumbnail;
    private Integer itemsPerPack;
    private Double size;
    private Double price;
    private Integer pickupOrder;
    private String humanVolume;
    private String pricerType;
    private Integer availableStock;
    private List<String> eans = null;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getRetailerPrice() {
        return retailerPrice;
    }

    public void setRetailerPrice(Double retailerPrice) {
        this.retailerPrice = retailerPrice;
    }

    public Integer getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(Integer retailerId) {
        this.retailerId = retailerId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getItemsPerPack() {
        return itemsPerPack;
    }

    public void setItemsPerPack(Integer itemsPerPack) {
        this.itemsPerPack = itemsPerPack;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getPickupOrder() {
        return pickupOrder;
    }

    public void setPickupOrder(Integer pickupOrder) {
        this.pickupOrder = pickupOrder;
    }

    public String getHumanVolume() {
        return humanVolume;
    }

    public void setHumanVolume(String humanVolume) {
        this.humanVolume = humanVolume;
    }

    public String getPricerType() {
        return pricerType;
    }

    public void setPricerType(String pricerType) {
        this.pricerType = pricerType;
    }

    public Integer getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
    }

    public List<String> getEans() {
        return eans;
    }

    public void setEans(List<String> eans) {
        this.eans = eans;
    }

}
