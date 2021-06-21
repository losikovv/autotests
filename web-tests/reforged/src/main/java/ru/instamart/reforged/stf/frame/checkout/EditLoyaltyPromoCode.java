package ru.instamart.reforged.stf.frame.checkout;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Input;

public class EditLoyaltyPromoCode implements CommonFrameButtons {

    private final Input value = new Input(By.xpath("//div[@class='modal-form']//input[contains(@class, 'checkout-input')]"));

    @Step("Заполнить поле номер бонусной карты {0}")
    public void fillValue(String data) {
        value.fill(data);
    }
}
