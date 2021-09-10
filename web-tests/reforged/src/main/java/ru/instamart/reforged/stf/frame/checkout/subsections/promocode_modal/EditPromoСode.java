package ru.instamart.reforged.stf.frame.checkout.subsections.promocode_modal;

import io.qameta.allure.Step;
import ru.instamart.kraken.testdata.UserData;

public class EditPromoСode implements EditPromoСodeCheck{

    @Step("Ввести промокод {0} в инпут")
    public void enterPromoCode(String promo) {
        promoCodeInput.fill(promo);
    }

    @Step("Нажать кнопку применения промокода")
    public void applyPromoCode() {
        promoCodeApplyButton.click();
    }
}
