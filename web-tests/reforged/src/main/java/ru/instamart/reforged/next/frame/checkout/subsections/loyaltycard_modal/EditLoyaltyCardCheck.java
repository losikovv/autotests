package ru.instamart.reforged.next.frame.checkout.subsections.loyaltycard_modal;

import io.qameta.allure.Step;
import org.testng.Assert;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface EditLoyaltyCardCheck extends EditLoyaltyCardElement {

    @Step("Проверяем, что показывается алерт пустого инпута карты")
    default void checkEmptyCardNumberAlert() {
        waitAction().shouldBeVisible(emptyCardNumberAlert);
    }

    @Step("Проверяем, что показывается алерт некорректного инпута карты")
    default void checkWrongCardNumberAlert() {
        waitAction().shouldBeVisible(wrongCardNumberAlert);
    }

    @Step("Проверяем, что в инпуте карты лояльности прописано значение {0}")
    default void checkCardNumberInputValue(final String expValue) {
        Assert.assertEquals(value.getValue(), expValue);
    }
}
