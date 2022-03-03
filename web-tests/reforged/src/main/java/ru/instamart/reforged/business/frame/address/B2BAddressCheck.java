package ru.instamart.reforged.business.frame.address;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface B2BAddressCheck extends B2BAddressElement, Check {

    @Step("Фрейм адреса доставки не отображается")
    default void checkAddressModalIsNotVisible() {
        waitAction().shouldNotBeVisible(addressModal);
    }

    @Step("Маркер не отображается")
    default void checkMarkerOnMapInAdviceIsNotVisible() {
        waitAction().shouldNotBeVisible(markerImageOnMapInAdvice);
    }

    @Step("Проверяем, что яндекс карты готовы к работе")
    default void checkYmapsReady() {
        waitAction().shouldBeVisible(ymapReady);
    }
}
