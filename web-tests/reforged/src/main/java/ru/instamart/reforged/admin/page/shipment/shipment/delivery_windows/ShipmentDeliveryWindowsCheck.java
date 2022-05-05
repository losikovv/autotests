package ru.instamart.reforged.admin.page.shipment.shipment.delivery_windows;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Check;

import java.util.List;
import java.util.Set;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ShipmentDeliveryWindowsCheck extends Check, ShipmentDeliveryWindowsElement {

    @Step("Проверяем, что отобразилась информация о выбранном интервале доставки")
    default void checkSavedDeliveryIntervalVisible() {
        waitAction().shouldBeVisible(savedShipmentInterval);
    }

    @Step("Проверяем, что отобразилась информация о выбранном магазине")
    default void checkSavedShopVisible() {
        waitAction().shouldBeVisible(savedShop);
    }

    @Step("Проверяем, что указанный интервал доставки соответствует ожидаемому: {expectedDeliveryIntervalText}")
    default void checkSavedDeliveryIntervalTextContains(final String expectedDeliveryIntervalText) {
        Assert.assertTrue(
                StringUtil.hyphenReplace(savedShipmentInterval.getText()).contains(StringUtil.hyphenReplace(expectedDeliveryIntervalText)),
                String.format("Указанный слот доставки: '%s' не содержит ожидаемый текст: '%s'", savedShipmentInterval.getText(), expectedDeliveryIntervalText));
    }

    @Step("Проверяем, что список причин изменения интервала доставки отображается")
    default void checkReasonsDisplayed() {
        waitAction().shouldBeVisible(deliveryChangeReasons);
    }

    @Step("Проверяем, что отображается всплывающее уведомление")
    default void checkNoticePopupDisplayed() {
        waitAction().shouldBeVisible(noticePopup);
    }

    @Step("Проверяем, что текст всплывающего уведомления соответствует ожидаемому: '{expectedText}'")
    default void checkNoticeTextEquals(final String expectedText) {
        Assert.assertEquals(noticePopup.getText(), expectedText, "Текст всплывающего уведомления отличается от ожидаемого");
    }

    @Step("Проверяем информацию о слотах в доступных интервалах доставки")
    default void checkSlotsInfoInAvailableIntervals(final Set<String> availableIntervals) {
        availableIntervals.forEach(
                interval -> krakenAssert.assertFalse(StringUtil.getSlotsFromDeliveryText(interval).isEmpty(),
                        "Не удалось обнаружить информацию о слотах в интервале " + interval));
        krakenAssert.assertAll();
    }
}
