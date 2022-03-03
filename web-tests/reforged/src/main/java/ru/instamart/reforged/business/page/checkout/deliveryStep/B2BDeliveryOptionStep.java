package ru.instamart.reforged.business.page.checkout.deliveryStep;

import io.qameta.allure.Step;

public class B2BDeliveryOptionStep implements B2BDeliveryOptionCheck {

    @Step("Нажимаем 'Продолжить'")
    public void clickToSubmitForDelivery() {
        submit.scrollTo();
        submit.hoverAndClick();
    }
}
