package ru.instamart.reforged.stf.page.checkout.firstStep;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

public interface DeliveryOptionCheck extends Check, DeliveryOptionElement {

    @Step("Проверяем, что радиобаттон 'Для себя' выбран")
    default void checkForSelfIsSelected(boolean state) {
        krakenAssert.assertTrue(state, "радиобаттон 'Для себя' не выбран");
    }

    @Step("Проверяем, что радиобаттон 'Для бизнеса' выбран")
    default void checkForBusinessIsSelected(boolean state) {
        krakenAssert.assertTrue(state, "радионбаттон 'Для бизнеса' не выбран");
    }

    @Step("Проверяем, значение в поле 'Номер квартиры / офис'")
    default void checkApartmentValue(String actual, String expected) {
        krakenAssert.assertEquals(actual, expected, "значение в поле адреса неверное");
    }

    @Step("Проверяем, значение в поле 'Этаж'")
    default void checkFloorValue(String actual, String expected) {
        krakenAssert.assertEquals(actual, expected, "значение в поле этаж неверное");
    }

    @Step("Проверяем, что выбран чекбокс 'Есть лифт'")
    default void checkElevatorIsSelected(boolean state) {
        krakenAssert.assertTrue(state, "чекбокс 'Есть лифт' не выбран");
    }

    @Step("Проверяем, что не выбран чекбокс 'Есть лифт'")
    default void checkElevatorIsNotSelected(boolean state) {
        krakenAssert.assertFalse(state, "чекбокс 'Есть лифт' выбран");
    }

    @Step("Проверяем, значение в поле 'Подъезд'")
    default void checkEntranceValue(String actual, String expected) {
        krakenAssert.assertEquals(actual, expected, "значение в поле 'Подъезд' неверное");
    }

    @Step("Проверяем, значение в поле 'Код домофона'")
    default void checkDoorPhoneValue(String actual, String expected) {
        krakenAssert.assertEquals(actual, expected, "значение в поле 'Код домофона' неверное");
    }

    @Step("Проверяем, что выбран чекбокс 'Бесконтактная доставка'")
    default void checkContactlessDeliveryIsSelected(boolean state) {
        krakenAssert.assertTrue(state, "чекбокс 'Бесконтактная доставка' не выбран");
    }

    @Step("Проверяем, что не выбран чекбокс 'Бесконтактная доставка'")
    default void checkContactlessDeliveryIsNotSelected(boolean state) {
        krakenAssert.assertFalse(state, "чекбокс Бесконтактная доставка не выбран");
    }

    @Step("Проверяем, значение в поле 'Код домофона'")
    default void checkCommentsValue(String actual, String expected) {
        krakenAssert.assertEquals(actual, expected, "значение в поле Код домофона неверное");
    }
}