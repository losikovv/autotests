package ru.instamart.reforged.next.frame.address;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface AddressLargeCheck extends AddressLargeElement, Check {

    @Step("Проверяем, что яндекс карты готовы к работе")
    default void checkYmapsReady() {
        waitAction().shouldBeVisible(addressModal);
    }

    @Step("Проверяем, что фрейм выбора адреса не отображается")
    default void checkAddressModalNotVisible() {
        waitAction().shouldNotBeVisible(addressModal);
    }

    @Step("Проверяем, что маркер 'Адрес можно выбирать прямо на карте' не отображается")
    default void checkMarkerOnMapInAdviceIsNotVisible() {
        waitAction().shouldNotBeVisible(markerSelectOnMap);
    }

    @Step("Проверяем, что адрес находится не в зоне доставки")
    default void checkIsAddressOutOfZone() {
        waitAction().shouldBeVisible(outOfDeliveryMessage, "Не появилось сообщение о том, что адрес вне зоны доставки");
    }
}
