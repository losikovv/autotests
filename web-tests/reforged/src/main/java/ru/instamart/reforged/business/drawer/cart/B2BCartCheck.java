package ru.instamart.reforged.business.drawer.cart;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface B2BCartCheck extends Check, B2BCartElement {

    @Step("Проверяем, что корзина открыта")
    default void checkCartOpen() {
        waitAction().shouldBeVisible(cartContainer);
        cartContainer.should().animationFinished();
    }

    @Step("Проверяем, что корзина закрыта")
    default void checkCartClose() {
        cartContainer.should().invisible();
    }

    @Step("Проверка что корзина пуста")
    default void checkCartEmpty() {
        waitAction().shouldBeVisible(cartIsEmptyPlaceholder);
    }

    @Step("Проверяем, что корзина не пуста")
    default void checkCartNotEmpty() {
        cartIsEmptyPlaceholder.should().invisible();
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

    @Step("Проверяем, что количество единиц товара равно: {itemCount}")
    default void checkDisplayedItemCount(final String itemCount) {
        //Максимальное количество товаров при заказе через сайт - 199 шт, при вводе большего значения должно сбрасываться до 199
        Assert.assertEquals(itemCounter.getText(), itemCount, "Количество единиц товара в корзине отличается от ожидаемого");
    }

    @Step("Проверяем, что кнопка 'Сделать заказ' доступна")
    default boolean checkOrderButtonIsEnabled() {
        return waitAction().shouldBeVisible(submitCheckout).isEnabled();
    }
}
