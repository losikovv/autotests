package ru.instamart.reforged.stf.frame.checkout.subsections;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Input;

public class EditPhoneNumber implements CommonFrameButtons {

    private final Input phoneNumber = new Input(By.xpath("//input[@id='phone-input']"));

    @Step("Заполнить номер телефона")
    public void fillPhoneNumber(String data) {
        phoneNumber.fill(data);
    }

    @Step("Очистить поле номер")
    public void clearPhoneNumber() {
        phoneNumber.clear();
    }
}
