package ru.instamart.reforged.admin.page.settings.shipping_method;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ShippingMethodCheck extends Check, ShippingMethodElement {

    @Step("В селекторе доступен пункт {0}")
    default void checkValueInSelector(final String value) {
        waitAction().shouldBeVisible(selectorValue, value);
    }

    @Step("Отображается ошибка недопустимого интервала")
    default void checkIntervalError() {
        waitAction().shouldBeVisible(intervalError);
    }

    @Step("Кнопка 'Применить изменения' активна")
    default void checkSubmitButtonActive() {
        waitAction().shouldBeClickable(submitChanges);
    }
}
