package ru.instamart.reforged.stf.drawer.cart_new;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.data.ItemData;
import ru.instamart.reforged.data.RetailerData;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Отдельный магазин с товарами в корзине
 */
public class Retailer {

    Element retailerContainer; // = new Element(By.xpath("//div[@class='cart-retailer']"));
    By name = By.xpath(".//div[@class='cart-retailer-details__name']");
    By itemsCountInHeader = By.xpath("(.//div[@class='cart-retailer-details__well'])[1]");
    By totalWeight = By.xpath(".//div[@class='cart-retailer-details__weight']");
    By totalAmount = By.xpath("(.//div[@class='cart-retailer-details__well'])[2]");
    By nearestDeliveryInfo = By.xpath("(.//div[@class='retailer-delivery__summary'])[1]");
    By alert = By.xpath(".//div[@class='cart-retailer__alert']");
    By buttonRemoveShipments = By.xpath(".//button[@data-qa='cart_remove_shipments_button']");

    By itemInList = By.xpath(".//div[@data-qa='line-item']");

    public Retailer(Element retailerContainer) {
        this.retailerContainer = retailerContainer;
    }

    public String getName() {
        return retailerContainer.getInnerElement(name).getText();
    }

    public List<Item> getAllItems() {
        return retailerContainer.getInnerElements(itemInList).stream().map(Item::new).collect(Collectors.toList());
    }

    public boolean isAlertDisplayed() {
        return !retailerContainer.getInnerElements(alert).isEmpty();
    }

    public RetailerData getRetailerData() {
        return new RetailerData()
                .setName(getName())
                .setItemsCountInList(getItemsCountInHeader())
                .setTotalWeight(getTotalWeight())
                .setTotalAmount(getTotalAmount())
                .setDeliveryInfo(getNearestDeliveryInfo())
                .setItemsData(getItemsData());
    }

    private String getItemsCountInHeader() {
        return retailerContainer.getInnerElement(itemsCountInHeader).getText();
    }

    private String getTotalWeight() {
        return retailerContainer.getInnerElement(totalWeight).getText();
    }

    private String getTotalAmount() {
        return retailerContainer.getInnerElement(totalAmount).getText();
    }

    private String getNearestDeliveryInfo() {
        return retailerContainer.getInnerElement(nearestDeliveryInfo).getText();
    }

    public String getAlertText() {
        return retailerContainer.getInnerElement(alert).getText();
    }

    private List<ItemData> getItemsData() {
        return getAllItems().stream().map(Item::getItemData).collect(Collectors.toList());
    }

    public void removeRetailer(){
        retailerContainer.getInnerElement(buttonRemoveShipments).click();
    }

}
