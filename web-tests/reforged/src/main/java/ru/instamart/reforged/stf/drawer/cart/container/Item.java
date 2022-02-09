package ru.instamart.reforged.stf.drawer.cart.container;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Container;
import ru.instamart.reforged.core.component.inner.InnerButton;
import ru.instamart.reforged.core.component.inner.InnerElement;
import ru.instamart.reforged.core.component.inner.InnerInput;

import static ru.instamart.reforged.core.Check.krakenAssert;
import static ru.instamart.reforged.core.Kraken.waitAction;

/**
 * Товар в корзине
 */
public final class Item extends Container {

    private final InnerButton buttonOpenItemPopupInfo = new InnerButton(getContainer(), By.xpath(".//a[@data-qa='open-button']"), "Открыть карточку товара");
    private final InnerButton buttonDeleteItem = new InnerButton(getContainer(), By.xpath(".//button[@data-qa='cart_delete_item_button']"), "Кнопка удалить товар");
    private final InnerButton buttonIncreaseItemsCount = new InnerButton(getContainer(), By.xpath(".//button[@data-qa='increase-button']"), "Кнопка увеличить количество товара");
    private final InnerButton buttonDecreaseItemsCount = new InnerButton(getContainer(), By.xpath(".//button[@data-qa='decrease-button']"), "Кнопка уменьшить количество товара");
    private final InnerInput itemCountInput = new InnerInput(getContainer(), By.xpath(".//div[@data-qa='line-item-counter']//span"), "Поле ввода кол-ва товара");
    private final InnerElement itemName = new InnerElement(getContainer(), By.xpath(".//dt"), "Название товара");
    private final InnerElement itemPackageSize = new InnerElement(getContainer(), By.xpath(".//dd"), "Размер упаковки товара");
    private final InnerElement itemsAmount = new InnerElement(getContainer(), By.xpath(".//button[@data-qa='cart_delete_item_button']/../div"), "Общая стоимость товара");
    private final InnerElement costSpinner = new InnerElement(getContainer(), By.xpath("//div[@data-qa='line-item']//span[contains(text(),'Загрузка')]"), "Спиннер пересчета цены позиции");

    public Item(final WebElement container) {
        super(container);
    }

    @Step("Удаляем товар")
    public void deleteItem() {
        itemName.getActions().mouseOver();
        buttonDeleteItem.getActions().moveToElementAndClick();
    }

    @Step("Открываем продуктовую карту")
    public void openProductCart() {
        buttonOpenItemPopupInfo.click();
    }

    @Step("Получаем количество единиц продукта в корзине")
    public int getCount() {
        return itemCountInput.getNumericValue();
    }

    @Step("Получаем наименование продукта")
    public String getName() {
        return itemName.getText();
    }

    @Step("Сравниваем кол-во штук товара с ожидаемым значением {0}")
    public void compareItemQuantityInCart(final int expected) {
        krakenAssert.assertEquals(itemCountInput.getNumericValue(), expected, "Количество штук товара в корзине отличается от ожидаемого");
    }

    @Step("Проверяем, что спиннер пропал")
    public void checkSpinnerIsNotVisible() {
        waitAction().shouldNotBeVisible(costSpinner);
    }

    @Step("Проверяем, что спиннер отображается")
    public void checkSpinnerIsVisible() {
        waitAction().shouldBeVisible(costSpinner);
    }

    @Step("Кликаем на кнопку 'Уменьшить' количество товара")
    public void increaseCount() {
        itemCountInput.getActions().mouseOver(); //кнопка становится видимой только после того, как наводимся на поле
        buttonIncreaseItemsCount.getActions().moveToElementAndClick();
    }

    @Step("Кликаем на кнопку 'Увеличить' количество товара")
    public void decreaseCount() {
        itemCountInput.getActions().mouseOver(); //кнопка становится видимой только после того, как наводимся на поле
        buttonDecreaseItemsCount.getActions().moveToElementAndClick();
    }

    @Step("Получаем размер упаковки продукта")
    private String getPackageSize() {
        return itemPackageSize.getText();
    }

    @Step("Получаем итоговую стоимость продукта")
    private String getTotalAmount() {
        return itemsAmount.getText();
    }
}
