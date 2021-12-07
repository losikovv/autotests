package ru.instamart.reforged.stf.frame.checkout.subsections.loyaltycard_modal;

import io.qameta.allure.Step;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface EditLoyaltyCardCheck extends EditLoyaltyCardElement {

    @Step("Модальное окно открыто")
    default void checkModalWindow() {
        waitAction().shouldBeVisible(modal);
        waitAction().shouldNotBeAnimated(modal);
    }
}
