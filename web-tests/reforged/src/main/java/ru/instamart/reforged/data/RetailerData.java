package ru.instamart.reforged.data;

import java.util.List;

/**
 * Класс с данными ритейлера (инфа, относящаяся к магазину)
 */
public class RetailerData {

    private String name;
    private String itemsCount;
    private String totalWeight;
    private String totalAmount;
    private String deliveryInfo;
    private String alertInfo;
    private List<ItemData> itemsData;

    public String getName() {
        return name;
    }

    public RetailerData setName(String name) {
        this.name = name;
        return this;
    }

    public String getItemsCount() {
        return itemsCount;
    }

    public RetailerData setItemsCountInList(String itemCount) {
        this.itemsCount = itemCount;
        return this;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public RetailerData setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
        return this;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public RetailerData setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public String getDeliveryInfo() {
        return deliveryInfo;
    }

    public RetailerData setDeliveryInfo(String deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
        return this;
    }

    public String getAlertInfo() {
        return alertInfo;
    }

    public RetailerData setAlertInfo(String alertInfo) {
        this.alertInfo = alertInfo;
        return this;
    }

    public List<ItemData> getItemsData() {
        return itemsData;
    }

    public RetailerData setItemsData(List<ItemData> itemsData) {
        this.itemsData = itemsData;
        return this;
    }

    @Override
    public String toString() {
        return "name: " + name + "; itemCount: " + itemsCount + "; totalWeight: " + totalWeight +
                "; totalAmount:" + totalAmount + "; deliveryInfo: " + deliveryInfo + "\n" + itemsData;
    }
}
