package ru.instamart.reforged.stf.frame.address;

import io.qameta.allure.Step;

import static org.testng.Assert.assertEquals;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface AddressCheck extends AddressElement {

    default void checkAddressModalIsNotVisible() {
        waitAction().shouldNotBeVisible(addressModal);
    }

    default void checkMarkerOnMapInAdviceIsNotVisible() {
        waitAction().shouldNotBeVisible(markerImageOnMapInAdvice);
    }

    @Step("Проверяем, что адрес находится не в зоне доставки")
    default void checkIsAddressOutOfZone() {
        assertEquals(outOfShippingZone.getText(), "Адрес не в зоне доставки");
    }
}
