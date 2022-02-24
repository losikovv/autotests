package ru.instamart.reforged.business.drawer.cart;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CartCheck extends Check, CartElement {

    @Step("Проверяем, что корзина открыта")
    default void checkCartOpen() {
        waitAction().shouldBeVisible(cartContainer);
        waitAction().shouldNotBeAnimated(cartContainer);
    }

    @Step("Проверяем, что корзина закрыта")
    default void checkCartClose() {
        waitAction().shouldNotBeVisible(cartContainer);
        waitAction().shouldNotBeAnimated(cartContainer);
    }

    @Step("Проверка что корзина пуста")
    default void checkCartEmpty() {
        waitAction().shouldBeVisible(cartIsEmptyPlaceholder);
    }

    @Step("Проверяем, что корзина не пуста")
    default void checkCartNotEmpty() {
        waitAction().shouldNotBeVisible(cartIsEmptyPlaceholder);
    }

    @Step("Проверяем, что количество товаров в корзине равно: {expectedCount}")
    default void checkItemsCount(final int expectedCount) {
        Assert.assertEquals(items.elementCount(), expectedCount, "Количество товаров в корзине отличается от ожидаемого");
    }

    @Step("Проверяем, что количество ритейлеров в корзине равно: {expectedCount}")
    default void checkRetailersCount(final int expectedCount) {
        Assert.assertEquals(retailers.elementCount(), expectedCount, "Количество ритейлеров в корзине отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается 'НДС к возврату'")
    default void checkTotalVatDisplayed() {
        waitAction().shouldBeVisible(totalVat);
    }

    @Step("Проверяем, что количестов единиц товара равно: {itemCount}")
    default void checkDisplayedItemCount(final String itemCount) {
        //Максимальное количество товаров при заказе через сайт - 199 шт, при вводе большего значения должно сбрасываться до 199
        Assert.assertEquals(itemCounter.getText(), itemCount, "Количество единиц товара в корзине отличается от ожидаемого");
    }
}
