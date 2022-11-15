package ru.instamart.reforged.stf.page.checkout_new.sber_spasibo_card_modal;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

public interface SberSpasiboCardCheck extends Check, SberSpasiboCardElement {

    @Step("Проверяем, что модальное окно отображается")
    default void checkModalVisible() {
        modal.should().visible();
    }

    @Step("Проверяем, что модальное окно не отображается")
    default void checkModalNotVisible() {
        Assert.assertTrue(modal.is().invisible());
    }

    @Step("Проверка отображения ошибки заполнения поля ввода '{0}'")
    default void checkValidationErrorVisible(final String errorText) {
        cardInputError.should().visible(errorText);
    }

    @Step("Проверка отображения ошибки добавления карты '{0}'")
    default void checkErrorAddingCardDisplayed(final String errorText) {
        cardModalError.should().visible(errorText);
    }

    @Step("Проверка отображения поля с бонусным счетом")
    default void checkBonusesFieldDisplayed() {
        bonusesField.should().visible();
    }
}