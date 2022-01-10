package ru.instamart.reforged.stf.drawer.cart_new;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.data.ItemData;

import static java.lang.Thread.sleep;

/**
 * Товар в корзине
 */
public class Item {

    WebElement itemContainer; //= By.xpath(".//div[@data-qa='line-item']");
    By buttonOpenItemPopupInfo = By.xpath(".//a[@data-qa='open-button']");
    By buttonDeleteItem = By.xpath(".//button[@data-qa='cart_delete_item_button']");
    By buttonIncreaseItemsCount = By.xpath(".//button[@data-qa='increase-button']");
    By buttonDecreaseItemsCount = By.xpath(".//button[@data-qa='decrease-button']");
    By inputItemsCount = By.xpath(".//input");
    By itemCount = By.xpath(".//div[@data-qa='line-item-counter']");
    By itemName = By.xpath(".//dt");
    By itemPackageSize = By.xpath(".//dd");
    By itemsAmount = By.xpath(".//button[@data-qa='cart_delete_item_button']/../div");

    public Item(WebElement container) {
        this.itemContainer = container;
    }

    public void deleteItem() {
        Actions action = new Actions(Kraken.getWebDriver());
        action.moveToElement(itemContainer.findElement(itemsAmount)).build().perform();
        action.moveToElement(itemContainer.findElement(buttonDeleteItem)).click().build().perform();
    }

    public void openItemPopupInfo(){
        itemContainer.findElement(buttonOpenItemPopupInfo).click();
    }

    public ItemData getItemData() {
        return new ItemData()
                .setCount(getCount())
                .setName(getName())
                .setPackageSize(getPackageSize())
                .setTotalAmount(getTotalAmount());
    }

    private String getCount() {
        return itemContainer.findElement(itemCount).getText();
    }

    private String getName() {
        return itemContainer.findElement(itemName).getText();
    }

    private String getPackageSize() {
        return itemContainer.findElement(itemPackageSize).getText();
    }

    private String getTotalAmount() {
        return itemContainer.findElement(itemsAmount).getText();
    }
}
