package ru.instamart.reforged.stf.drawer.cart_new.container;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Container;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.inner.InnerButton;
import ru.instamart.reforged.core.component.inner.InnerCollectionComponent;
import ru.instamart.reforged.core.component.inner.InnerElement;
import ru.instamart.reforged.data.ItemData;
import ru.instamart.reforged.data.RetailerData;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Отдельный магазин с товарами в корзине
 */
@Slf4j
public final class Retailer extends Container {

    private final InnerElement name = new InnerElement(getContainer(), By.xpath(".//div[@class='cart-retailer-details__name']"), "empty");
    private final InnerElement itemsCountInHeader = new InnerElement(getContainer(), By.xpath("(.//div[@class='cart-retailer-details__well'])[1]"), "empty");
    private final InnerElement totalWeight = new InnerElement(getContainer(), By.xpath(".//div[@class='cart-retailer-details__weight']"), "empty");
    private final InnerElement totalAmount = new InnerElement(getContainer(), By.xpath("(.//div[@class='cart-retailer-details__well'])[2]"), "empty");
    private final InnerElement nearestDeliveryInfo = new InnerElement(getContainer(), By.xpath("(.//div[@class='cart-retailer-details__well'])[2]"), "empty");
    private final InnerElement alert = new InnerElement(getContainer(), By.xpath(".//div[@class='cart-retailer__alert']"), "empty");
    private final InnerButton buttonRemoveShipments = new InnerButton(getContainer(), By.xpath(".//button[@data-qa='cart_remove_shipments_button']"), "empty");
    private final InnerCollectionComponent itemInList = new InnerCollectionComponent(getContainer(), By.xpath(".//div[@data-qa='line-item']"), "empty");

    public Retailer(final WebElement container) {
        super(container);
    }

    public String getName() {
        return name.getText();
    }

    public void isAlertNotDisplayed() {
        Kraken.waitAction().shouldNotBeVisible(alert, getContainer());
    }

    @Step("Получить {0} товар")
    public Item getItem(final int order) {
        final List<Item> items = getAllItems();
        if (items.size() >= order) {
            return items.get(order);
        } else {
            log.error("Try to get undefined item {} from item list {}", order, items);
            return new Item(getContainer());
        }
    }

    @Step("Количество товаров у ретейлера")
    public int itemCount() {
        return getAllItems().size();
    }

    public RetailerData getRetailerData() {
        return RetailerData.builder()
                .name(getName())
                .itemsCount(getItemsCountInHeader())
                .totalWeight(getTotalWeight())
                .totalAmount(getTotalAmount())
                .deliveryInfo(getNearestDeliveryInfo())
                .itemsData(getItemsData())
                .build();
    }

    public void removeRetailer() {
        buttonRemoveShipments.click();
    }

    private String getItemsCountInHeader() {
        return itemsCountInHeader.getText();
    }

    private String getTotalWeight() {
        return totalWeight.getText();
    }

    private String getTotalAmount() {
        return totalAmount.getText();
    }

    private String getNearestDeliveryInfo() {
        return nearestDeliveryInfo.getText();
    }

    private List<Item> getAllItems() {
        return itemInList.getComponents().stream().map(Item::new).collect(Collectors.toList());
    }

    private List<ItemData> getItemsData() {
        return getAllItems().stream().map(Item::getItemData).collect(Collectors.toList());
    }
}
