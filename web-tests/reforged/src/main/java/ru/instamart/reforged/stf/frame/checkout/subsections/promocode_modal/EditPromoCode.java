package ru.instamart.reforged.stf.frame.checkout.subsections.promocode_modal;

import io.qameta.allure.Step;

public class EditPromoCode implements EditPromoCodeCheck {

    @Step("Ввести промокод {0} в инпут")
    public void enterPromoCode(String promo) {
        promoCodeInput.fill(promo);
    }

    @Step("Нажать кнопку применения промокода")
    public void applyPromoCode() {
        promoCodeApplyButton.click();
    }

    @Step("Нажать кнопку отмены промокода")
    public void cancelPromoCode() {
        promoCodeCancelButton.click();
    }

    @Step("Нажать кнопку закрытия модального окна ввода промокода")
    public void close() {
        closePromoCodeModalButton.click();
    }
}
