package ru.instamart.reforged.business.frame.address;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface B2BAddressCheck extends B2BAddressElement, Check {

    @Step("Фрейм адреса доставки не отображается")
    default void checkAddressModalIsNotVisible() {
        Assert.assertTrue(addressModal.is().invisible());
    }

    @Step("Маркер не отображается")
    default void checkMarkerOnMapInAdviceIsNotVisible() {
        Assert.assertTrue(markerImageOnMapInAdvice.is().invisible());
    }

    @Step("Проверяем, что яндекс карты готовы к работе")
    default void checkYmapsReady() {
        waitAction().shouldBeVisible(ymapReady);
    }
}
