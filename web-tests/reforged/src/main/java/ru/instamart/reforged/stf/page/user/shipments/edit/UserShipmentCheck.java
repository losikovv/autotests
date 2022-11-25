package ru.instamart.reforged.stf.page.user.shipments.edit;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

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

    @Step("Проверяем, что отображается стоимость сборки")
    default void checkAssemblyCostVisible() {
        Kraken.waitAction().shouldBeVisible(assemblyCost);
    }

    @Step("Проверяем, что отображается промокод")
    default void checkPromoCodeVisible() {
        Kraken.waitAction().shouldBeVisible(promoCode);
    }

    @Step("Проверяем, что стоимость сборки и доставки заказа: '{expectedValue}'")
    default void checkShipmentCost(final String expectedValue) {
        Assert.assertEquals(shipmentCost.getText(), expectedValue, "Стоимость сборки и доставки отличается от ожидаемой");
    }

    @Step("Проверяем, что отображается сумма 'Итого'")
    default void checkTotalCostVisible() {
        Kraken.waitAction().shouldBeVisible(totalCost);
    }

    @Step("Проверяем, что отображается кнопка 'Изменить' (слот)")
    default void checkChangeDeliverySlotButtonVisible() {
        Kraken.waitAction().shouldBeVisible(changeDeliverySlot);
    }

    @Step("Проверяем, что появилось всплывающее сообщение")
    default void checkAlertVisible() {
        Kraken.waitAction().shouldBeVisible(alert);
    }

    @Step("Проверяем, что всплывающее сообщение не отображается")
    default void checkAlertNotVisible() {
        alert.should().invisible();
    }

    @Step("Проверяем что текст бабл-сообщения соответствует ожидаемому")
    default void checkAlertText(final String expectedText) {
        Assert.assertEquals(alert.getText(), expectedText, "Текст всплывающего сообщения отличается от ожидаемого");
    }

    @Step("Проверяем, что текущий интервал доставки: '{expectedValue}'")
    default void checkCurrentDeliveryInterval(final String expectedValue) {
        Assert.assertEquals(deliveryInterval.getText(), expectedValue, "Текущий интервал доставки отличается от ожидаемого");
    }

    @Step("Проверяем, что стоимость заказа 'Итого': '{expectedValue}'")
    default void checkOrderTotalCost(final String expectedValue) {
        Assert.assertEquals(totalCost.getText(), expectedValue, "Стоимость заказа отличается от ожидаемой");
    }

    @Step("Проверяем, что стоимость заказа 'Итого': '{expectedValue}'")
    default void checkOrderTotalCost(final double expectedValue) {
        Assert.assertEquals(StringUtil.stringToDouble(totalCost.getText()), expectedValue, "Стоимость заказа отличается от ожидаемой");
    }

    @Step("Проверяем, что стоимость заказа 'Итого' увеличилась'")
    default void checkOrderTotalCostIncreased(final double oldTotalCostValue) {
        Assert.assertTrue(StringUtil.stringToDouble(totalCost.getText()) > oldTotalCostValue,
                String.format("Текущая стоимость заказа: '%s' не больше первоначальной: '%s'", totalCost.getText(), oldTotalCostValue));
    }

    @Step("Проверяем, что стоимость заказа 'Итого' уменьшилась'")
    default void checkOrderTotalCostDecreased(final double oldTotalCostValue) {
        Assert.assertTrue(StringUtil.stringToDouble(totalCost.getText()) < oldTotalCostValue,
                String.format("Текущая стоимость заказа: '%s' не меньше первоначальной: '%s'", totalCost.getText(), oldTotalCostValue));
    }
}
