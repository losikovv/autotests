package ru.instamart.reforged.business.page.checkout;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface B2BCheckoutCheck extends Check, B2BCheckoutElement {

    @Step("Проверяем, что кнопка 'Оформить заказ' в чекауте видна")
    default void checkSubmitFromCheckoutVisible() {
        waitAction().shouldBeVisible(submitCheckoutInLastStep);
    }

    @Step("Проверяем, что кнопка 'Оформить заказ' в чекауте активна")
    default void checkSubmitFromCheckoutActive() {
        waitAction().shouldBeClickable(submitCheckoutInLastStep);
    }

    @Step("Проверяем что лоадер чекаута скрылся")
    default void checkCheckoutLoaderNotVisible() {
        Assert.assertTrue(checkoutLoader.is().invisible());
    }
}
