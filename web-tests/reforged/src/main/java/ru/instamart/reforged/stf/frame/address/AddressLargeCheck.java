package ru.instamart.reforged.stf.frame.address;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.assertEquals;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface AddressLargeCheck extends AddressLargeElement, Check {

    @Step("Проверяем, что яндекс карты готовы к работе")
    default void checkYmapsReady() {
        waitAction().shouldBeVisible(addressModal);
    }

    @Step("Проверяем, что фрейм выбора адреса не отображается")
    default void checkAddressModalNotVisible() {
        addressModal.should().invisible();
    }

    @Step("Проверяем, что маркер 'Адрес можно выбирать прямо на карте' не отображается")
    default void checkMarkerOnMapInAdviceIsNotVisible() {
        markerSelectOnMap.should().invisible();
    }

    @Step("Проверяем, что адрес находится не в зоне доставки")
    default void checkIsAddressOutOfZone() {
        waitAction().shouldBeVisible(outOfDeliveryMessage, "Не появилось сообщение о том, что адрес вне зоны доставки");
    }

    @Step("Проверяем, что отображается список магазинов")
    default void checkPickupStoresModalVisible() {
        waitAction().shouldBeVisible(storesModal);
    }

    @Step("Фрейм адреса доставки не отображается")
    default void checkAddressModalIsNotVisible() {
        addressModal.should().invisible();
    }

    @Step("Проверка что сохраненных адресов '{0}'")
    default void checkCountOfStoredAddresses(final int count) {
        assertEquals(foundedAddresses.elementCount(), count, "Недостаточное количество сохраненных адресов");
    }

    @Step("Проверка сохраненного адреса '{actual}' с ожидаемым '{expected}'")
    default void checkStoredAddresses(final String actual, final String expected) {
        assertEquals(actual, expected, "Адреса не совпадают");
    }
}
