package ru.instamart.reforged.stf.drawer.cart;

import io.qameta.allure.Step;
import org.testng.asserts.SoftAssert;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.assertEquals;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CartCheck extends Check, CartElement {
    SoftAssert softAssert = new SoftAssert();

    @Step("Проверяем, что кнопка заказа доступна")
    default boolean checkOrderButtonIsEnabled() {
        return waitAction().shouldBeVisible(submitOrder).isEnabled();
    }

    @Step("Проверяем, что спиннер пропал")
    default void checkSpinnerIsNotVisible() {
        waitAction().shouldNotBeVisible(costSpinner);
    }

    @Step("Проверяем, что стоимость увеличенного кол-во товаров {1} больше стартового {0}")
    default void checkIncreasedAmountMoreThanStart(double start, double increased) {
        softAssert.assertTrue(start < increased);
    }

    @Step("Проверяем, что стоимость увеличенного кол-во товаров {0} больше уменьшенного {1}")
    default void checkIncreasedAmountMoreThanDecreased(double increased, double decreased) {
        softAssert.assertTrue(decreased < increased);
    }

    @Step("Проверяем, что минимальная сумма первого заказа {0} больше повторного {1}")
    default void checkFirstMinAmountMoreThanRepeated(double first, double repeated) {
        softAssert.assertTrue(first > repeated);
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
    }

    @Step("Проверяем, что корзина открыта")
    default void checkCartClose() {
        waitAction().shouldNotBeVisible(cartDrawer);
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

    @Step("Сравнить количество товаров в корзине с ожидаемым значением {0}")
    default void compareItemsInCart(final int expected) {
        assertEquals(items.elementCount(), expected, "Количество товаров в корзине отличается от ожидаемого");
    }
}
