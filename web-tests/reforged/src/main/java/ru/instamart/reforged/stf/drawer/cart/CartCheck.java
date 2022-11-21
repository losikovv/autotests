package ru.instamart.reforged.stf.drawer.cart;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.assertTrue;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CartCheck extends Check, CartElement {

    @Step("Проверяем, что название товара в корзине {0} соответствует ожидаемому {1}")
    default void compareProductNameInCart(final String productNameActual, final String productNameExpected) {
        Assert.assertTrue(productNameActual.contains(productNameExpected.replaceAll("\\.{3}", "")),
                String.format("Название товара в корзине: '%s' не соответствует ожидаемому: '%s'", productNameActual, productNameExpected));
    }

    @Step("Проверяем, что минимальная сумма первого заказа {0} меньше повторного {1}")
    default void checkFirstMinAmountLessThanRepeated(final double first, final double repeated) {
        assertTrue(first < repeated,
                String.format("Минимальная сумма первого заказа %s больше чем сумма второго %s", first, repeated)
        );
    }

    @Step("Проверяем, что корзина открыта")
    default void checkCartOpen() {
        cartContainer.should().visible();
        cartContainer.should().animationFinished();
    }

    @Step("Проверяем, что корзина закрыта")
    default void checkCartClose() {
        cartContainer.should().invisible();
    }

    @Step("Проверка что корзина пуста")
    default void checkCartEmpty() {
        checkCartPlaceholderIsVisible();
    }

    @Step("Проверяем, что корзина не пуста")
    default void checkCartNotEmpty() {
        firstItem.shouldBe().visible();
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

    @Step("Проверяем, что первоначальная {0} цена товаров в корзине равна текущей {1}")
    default void checkAmountEquals(final double startOrderAmount, final double actualOrderAmount) {
        krakenAssert.assertEquals(startOrderAmount, actualOrderAmount, "Текущая цена товаров в корзине отличается от ожидаемой");
    }

    @Step("Проверяем, что первоначальная {0} цена товаров в корзине отличается от текущей {1}")
    default void checkAmountNotEquals(final double startOrderAmount, final double actualOrderAmount) {
        krakenAssert.assertNotEquals(startOrderAmount, actualOrderAmount, "Текущая цена товаров в корзине не отличается от первоначальной");
    }

    @Step("Проверяем, что анимация удаления завершена")
    default void checkDeleteAnimationOver() {
        items.should().animationFinished();
    }

    @Step("Проверяем, что в количество товаров в корзине равно: {expectedCount}")
    default void checkItemsCount(final int expectedCount) {
        Assert.assertEquals(items.elementCount(), expectedCount, "Количество товаров в корзине отличается от ожидаемого");
    }

    @Step("Проверяем, что в количество товаров в корзине больше чем: {expectedCount}")
    default void checkItemsCountMoreThan(final int expectedCount) {
        Assert.assertTrue(items.elementCount() > expectedCount, "Количество товаров в корзине отличается от ожидаемого");
    }

    @Step("Проверяем соответствие текущего количества товаров {0} ожидаемому {1}")
    default void checkItemsCount(final int actualItemsCount, final int expectedItemsCount) {
        Assert.assertEquals(actualItemsCount, expectedItemsCount, "Количество товаров в корзине не соответствует ожидаемому");
    }

    @Step("Проверяем, что количество магазинов в корзине равно {0}")
    default void checkRetailersCountShouldBe(int expectedRetailersCount) {
        Assert.assertTrue(waitAction().isElementCollectionSizeEqual(retailers, expectedRetailersCount));
    }

    @Step("Проверяем, что рителер '{retailerName}' не отображается в корзине")
    default void checkRetailerNotVisible(final String retailerName) {
        retailerByName.should().invisible(retailerName);
    }

    @Step("Проверяем, что в списке товаров присутствует: '{expectedItemName}'")
    default void checkItemListContains(final String expectedItemName){
        Assert.assertTrue(itemNames.getTextFromAllElements().contains(expectedItemName), "В списке товаров в корзине не найдено ожидаемого");
    }
}
