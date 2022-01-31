package ru.instamart.reforged.business.page.checkout.deliveryStep;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface DeliveryOptionCheck extends Check, DeliveryOptionElement {

    @Step("Проверяем, что кнопка 'Продолжить' шага 'Способ получения' видна")
    default void checkSubmitButtonVisible() {
        Kraken.waitAction().shouldBeVisible(submit);
    }

    @Step("Проверяем, что кнопка 'Продолжить' шага 'Способ получения' не видна")
    default void checkSubmitButtonNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(submit);
    }
}