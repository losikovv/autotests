package ru.instamart.reforged.stf.page.user.shipments.edit;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface UserShipmentCheck extends Check, UserShipmentElement {

    @Step("Проверяем, что текущий статус заказа: '{stateName}'")
    default void checkActiveShipmentState(final String stateName) {
        Assert.assertEquals(shipmentState.getText(), stateName, "Текущий статус заказа отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается номер заказа")
    default void checkShipmentNumberVisible() {
        Kraken.waitAction().shouldBeVisible(shipmentNumber);
    }

    @Step("Проверяем, что отображается адрес доставки")
    default void checkShippingAddressVisible() {
        Kraken.waitAction().shouldBeVisible(shippingAddress);
    }

    @Step("Проверяем, что метод оплаты: '{expectedPaymentMethod}'")
    default void checkPaymentMethodEquals(final String expectedPaymentMethod) {
        Assert.assertEquals(paymentMethod.getText(), expectedPaymentMethod, "Метод оплаты в заказе отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается стоимость товаров")
    default void checkProductsCostVisible() {
        Kraken.waitAction().shouldBeVisible(productsCost);
    }

    @Step("Проверяем, что отображается стоимость доставки")
    default void checkShipmentCostVisible() {
        Kraken.waitAction().shouldBeVisible(shipmentCost);
    }

    @Step("Проверяем, что отображается сумма 'Итого'")
    default void checkTotalCostVisible() {
        Kraken.waitAction().shouldBeVisible(totalCost);
    }
}
