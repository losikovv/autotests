package ru.instamart.reforged.business.page.checkout.deliveryStep;

import io.qameta.allure.Step;

public class DeliveryOptionStep implements DeliveryOptionCheck {

    @Step("Нажимаем 'Продолжить'")
    public void clickToSubmitForDelivery() {
        submit.scrollTo();
        submit.hoverAndClick();
    }
}
