package ru.instamart.reforged.business.page.checkout.deliveryStep;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface B2BDeliveryOptionCheck extends Check, B2BDeliveryOptionElement {

    @Step("Проверяем, что кнопка 'Продолжить' шага 'Способ получения' видна")
    default void checkSubmitButtonVisible() {
        Kraken.waitAction().shouldBeVisible(submit);
    }

    @Step("Проверяем, что кнопка 'Продолжить' шага 'Способ получения' не видна")
    default void checkSubmitButtonNotVisible() {
        Assert.assertTrue(submit.is().invisible());
    }
}