package ru.instamart.reforged.stf.page.checkout.fifthStep;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface PaymentStepCheck extends Check, PaymentStepElement {

    @Step("Проверяем, что кнопка заказа не кликабельна")
    default void checkSubmitOrderButtonNotClickable() {
        Assert.assertTrue(submitFromCheckoutColumn.is().unclickable());
    }

    @Step("Проверяем, что кнопка заказа кликабельна")
    default void checkSubmitOrderButtonClickable() {
        waitAction().shouldBeClickable(submitFromCheckoutColumn);
    }
}