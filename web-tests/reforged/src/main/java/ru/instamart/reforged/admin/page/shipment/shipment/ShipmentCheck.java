package ru.instamart.reforged.admin.page.shipment.shipment;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ShipmentCheck extends Check, ShipmentElement {

    @Step("Проверяем, что отобразилась информация о заказе")
    default void checkOrderInfoVisible() {
        waitAction().shouldBeVisible(orderInfo);
    }

    @Step("Проверяем, что заказ оформлен с промокодом '{promoCode}'")
    default void checkPromoCodeData(final String promoCode) {
        final var code = promoCodeData.getText().trim();
        Assert.assertEquals(promoCode, code, String.format("Промокод '%s' не соответствует ожидаемому '%s'", promoCode, code));
    }
}
