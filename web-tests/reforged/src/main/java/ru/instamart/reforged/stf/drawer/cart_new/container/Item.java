package ru.instamart.reforged.stf.drawer.cart_new.container;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.component.inner.InnerButton;
import ru.instamart.reforged.core.component.inner.InnerElement;
import ru.instamart.reforged.data.ItemData;
import ru.instamart.reforged.core.Container;

/**
 * Товар в корзине
 */
public final class Item extends Container {

    private final InnerButton buttonOpenItemPopupInfo = new InnerButton(getContainer(), By.xpath(".//a[@data-qa='open-button']"), "empty");
    private final InnerElement buttonDeleteItem = InnerElement.builder()
            .webElement(getContainer())
            .by(By.xpath(".//button[@data-qa='cart_delete_item_button']"))
            .description("Тестовый элемент")
            .build();
    private final InnerElement buttonIncreaseItemsCount = new InnerElement(getContainer(), By.xpath(".//button[@data-qa='increase-button']"), "empty");
    private final InnerElement buttonDecreaseItemsCount = new InnerElement(getContainer(), By.xpath(".//button[@data-qa='decrease-button']"), "empty");
    private final InnerElement inputItemsCount = new InnerElement(getContainer(), By.xpath(".//input"), "empty");
    private final InnerElement itemCount = new InnerElement(getContainer(), By.xpath(".//div[@data-qa='line-item-counter']"), "empty");
    private final InnerElement itemName = new InnerElement(getContainer(), By.xpath(".//dt"), "empty");
    private final InnerElement itemPackageSize = new InnerElement(getContainer(), By.xpath(".//dd"), "empty");
    private final InnerElement itemsAmount = new InnerElement(getContainer(), By.xpath(".//button[@data-qa='cart_delete_item_button']/../div"), "empty");

    public Item(final WebElement container) {
        super(container);
    }

    @Step("Удалить товар")
    public void deleteItem() {
        itemsAmount.hoverAction();
        buttonDeleteItem.clickAction();

    }

    @Step("Открыть продуктовую карту")
    public void openProductCart() {
        buttonOpenItemPopupInfo.click();
    }

    public ItemData getItemData() {
        return ItemData.builder()
                .count(getCount())
                .name(getName())
                .packageSize(getPackageSize())
                .totalAmount(getTotalAmount())
                .build();
    }

    private String getCount() {
        return itemCount.getText();
    }

    private String getName() {
        return itemName.getText();
    }

    private String getPackageSize() {
        return itemPackageSize.getText();
    }

    private String getTotalAmount() {
        return itemsAmount.getText();
    }
}
