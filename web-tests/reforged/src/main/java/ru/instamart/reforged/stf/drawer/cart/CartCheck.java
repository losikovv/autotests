package ru.instamart.reforged.stf.drawer.cart;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CartCheck extends Check, CartElement {

    @Step("Проверяем, что название товара в корзине {0} соответствует ожидаемому {1}")
    default void compareProductNameInCart(final String productNameActual, final String productNameExpected) {
        Assert.assertEquals(productNameActual, productNameExpected, "Название товара в корзине не соответствует ожидаемому");
    }

    @Step("Проверяем, что минимальная сумма первого заказа {0} больше повторного {1}")
    default void checkFirstMinAmountMoreThanRepeated(double first, double repeated) {
        krakenAssert.assertTrue(first > repeated);
    }

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
        checkCartPlaceholderIsVisible();
    }

    @Step("Проверяем, что корзина не пуста")
    default void checkCartNotEmpty() {
        waitAction().shouldNotBeVisible(cartIsEmptyPlaceholder);
    }

    @Step("Проверяем, что отображается плейсхолдер пустой корзины")
    default void checkCartPlaceholderIsVisible() {
        waitAction().shouldBeVisible(cartIsEmptyPlaceholder);
    }

    @Step("Проверяем, что кнопка 'Сделать заказ' доступна")
    default boolean checkOrderButtonIsEnabled() {
        return waitAction().shouldBeVisible(submitOrder).isEnabled();
    }

    @Step("Проверяем, что кнопка 'Сделать заказ' недоступна")
    default void checkOrderButtonIsNotEnabled() {
        Assert.assertFalse(waitAction().shouldBeVisible(submitOrder).isEnabled());
    }

    @Step("Проверяем, что в шторке корзины есть кнопка 'Закрыть'")
    default void checkCartCloseButtonIsVisible() {
        waitAction().shouldBeVisible(closeButton);
    }

    @Step("Проверяем, что кнопка 'Вернуться в каталог' доступна")
    default void checkCartReturnToCatalogButtonIsVisible() {
        waitAction().shouldBeVisible(backToCatalogButton);
    }

    @Step("Проверяем, что первоначальная {0} цена товаров в корзине отличается от текущей {1}")
    default void checkAmountNotEquals(final double startOrderAmount, final double actualOrderAmount) {
        krakenAssert.assertNotEquals(startOrderAmount, actualOrderAmount, "Текущая цена товаров в корзине не отличается от первоначальной");
    }

    @Step("Проверяем, что анимация удаления завершена")
    default void checkDeleteAnimationOver() {
        waitAction().shouldNotBeAnimated(items);
    }

    @Step("Проверяем соответствие количества товаров")
    default void checkItemsCount(final int actualItemsCount, final int expectedItemsCount) {
        Assert.assertEquals(actualItemsCount, expectedItemsCount, "Количество товаров в корзине не соответствует ожидаемому");
    }

    @Step("Проверяем, что количество магазинов в корзине равно {0}" )
    default void checkRetailersCountShouldBe(int expectedRetailersCount) {
        waitAction().elementCollectionSizeShouldBeEqual(retailers, expectedRetailersCount);
    }

}
