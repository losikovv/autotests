package ru.instamart.reforged.admin.page.sections.settings.shipping_method;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface ShippingMethodCheck extends Check, ShippingMethodElement {

    @Step("В селекторе доступен пункт {0}")
    default void checkValueInSelector(final String value) {
        Kraken.waitAction().shouldBeVisible(selectorValue, value);
    }
}
