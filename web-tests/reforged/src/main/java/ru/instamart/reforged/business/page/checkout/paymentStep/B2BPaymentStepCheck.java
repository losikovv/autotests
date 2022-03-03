package ru.instamart.reforged.business.page.checkout.paymentStep;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.assertTrue;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface B2BPaymentStepCheck extends Check, B2BPaymentStepElement {

    @Step("Проверяем, что кнопка 'Картой онлайн' видна")
    default void checkPaymentByCardOnlineVisible() {
        waitAction().shouldBeVisible(byCardOnline);
    }

    @Step("Проверяем, что название карты '{actual}' содержит текст '{expected}'")
    default void checkCardNameContains(String actual, String expected) {
        assertTrue(actual.contains(expected), String.format("Название карты '%s' не содержит текст '%s'", actual, expected));
    }

    @Step("Проверяем, что реквизиты '{actual}' содержат текст '{expected}'")
    default void checkRequisitesContains(String actual, String expected) {
        assertTrue(actual.contains(expected), String.format("Добавленные реквизиты '%s' не содержат текст '%s'", actual, expected));
    }
}