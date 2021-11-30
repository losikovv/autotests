package ru.instamart.reforged.stf.drawer.cart;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.assertEquals;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CartCheck extends Check, CartElement {

    @Step("Проверяем, что кнопка заказа доступна")
    default boolean checkOrderButtonIsEnabled() {
        return waitAction().shouldBeVisible(submitOrder).isEnabled();
    }

    @Step("Проверяем, что кнопка заказа недоступна")
    default void checkOrderButtonIsNotEnabled() {
        Assert.assertFalse(waitAction().shouldBeVisible(submitOrder).isEnabled());
    }

    @Step("Проверяем, что спиннер пропал")
    default void checkSpinnerIsNotVisible() {
        waitAction().shouldNotBeVisible(costSpinner);
    }

    @Step("Проверяем, что спиннер пропал")
    default void checkSpinnerIsVisible() {
        waitAction().shouldBeVisible(costSpinner);
    }

    @Step("Проверяем, что минимальная сумма первого заказа {0} больше повторного {1}")
    default void checkFirstMinAmountMoreThanRepeated(double first, double repeated) {
        krakenAssert.assertTrue(first > repeated);
    }

    @Step("Проверка что корзина пуста")
    default void checkCartEmpty() {
        waitAction().shouldBeVisible(placeholder);
    }

    @Step("Проверяем, что корзина не пустая")
    default void checkCartNotEmpty() {
        waitAction().shouldNotBeVisible(placeholder);
    }

    @Step("Проверяем, что корзина открыта")
    default void checkCartOpen() {
        waitAction().shouldBeVisible(cartDrawer);
        waitAction().shouldNotBeAnimated(cartDrawer);
    }

    @Step("Проверяем, что название продукта соответствует ожидаемому {0}")
    default void compareProductNameInCart(final String productNameExpected) {
        Assert.assertEquals(productNameExpected, firstProductName.getText(), "Имя товара в корзине не соответствует ожидаемому");
    }

    @Step("Проверяем, что корзина открыта")
    default void checkCartClose() {
        waitAction().shouldNotBeVisible(cartDrawer);
        waitAction().shouldNotBeAnimated(cartDrawer);
    }

    @Step("Проверяем, что анимация удаления завершена")
    default void checkDeleteAnimationOver() {
        waitAction().shouldNotBeAnimated(items);
    }

    @Step("Проверяем, что в шторке корзины есть кнопка закрытия")
    default void checkCartCloseButtonIsVisible() {
        waitAction().shouldBeVisible(close);
    }

    @Step("Проверяем, что в шторке корзины есть кнопка закрытия")
    default void checkCartPlaceholderIsVisible() {
        waitAction().shouldBeVisible(placeholder);
    }

    @Step("Проверяем, что в шторке корзины есть кнопка закрытия")
    default void checkCartReturnToCatalogButtonIsVisible() {
        waitAction().shouldBeVisible(returnToCatalog);
    }

    @Step("Сравнить количество уникальных товаров в корзине с ожидаемым значением {0}")
    default void compareItemsInCart(final int expected) {
        assertEquals(items.elementCount(), expected, "Количество товаров в корзине отличается от ожидаемого");
    }

    @Step("Сравнить кол-во штук первого в корзине с ожидаемым значением {0}")
    default void compareFirstItemQuantityInCart(final int expected) {
        krakenAssert.assertEquals(firstElementQuantity.getNumericValue(), expected, "Количество штук первого товара в корзине отличается от ожидаемого");
    }

    @Step("Сравнить неравенство цены в корзине с ожидаемым значением {0}")
    default void checkAmountNotEquals(final double expected, final double actual) {
        krakenAssert.assertNotEquals(expected, actual, "Цена товаров в корзине не отличается от первоначальной");
    }
}
