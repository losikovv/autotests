package ru.instamart.reforged.stf.frame.address;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.assertEquals;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface AddressCheck extends AddressElement, Check {

    @Step("Фрейм адреса доставки не отображается")
    default void checkAddressModalIsNotVisible() {
        Assert.assertTrue(addressModal.is().invisible());
    }

    @Step("Фрейм адреса доставки отображается")
    default void checkAddressModalVisible() {
        addressModal.should().visible();
        Assert.assertTrue(addressModal.is().animationFinished());
    }

    @Step("Маркер не отображается")
    default void checkMarkerOnMapInAdviceIsNotVisible() {
        Assert.assertTrue(markerImageOnMapInAdvice.is().invisible());
    }

    @Step("Проверяем, что адрес находится не в зоне доставки")
    default void checkIsAddressOutOfZone() {
        assertEquals(outOfShippingZone.getText(), "Адрес не в зоне доставки");
    }

    @Step("Проверяем, что яндекс карты готовы к работе")
    default void checkYmapsReady() {
        waitAction().shouldBeVisible(ymapReady);
    }

    @Step("Проверка что сохраненных адресов '{0}'")
    default void checkCountOfStoredAddresses(final int count) {
        assertEquals(storedAddresses.elementCount(), count, "Недостаточное количество сохраненных адресов");
    }

    @Step("Проверка сохраненного адреса '{actual}' с ожидаемым '{expected}'")
    default void checkStoredAddresses(final String actual, final String expected) {
        assertEquals(actual, expected, "Адреса не совпадают");
    }
}
