package ru.instamart.reforged.stf.frame.checkout;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;

public class EditPhoneNumber implements CommonFrameButtons {

    private final Input phoneNumber = new Input(By.xpath("//input[@id='phone-input']"));
//    private final Button delete = new Button(By.xpath("//button[@data-qa='checkout_phone_form_modal_delete_button']"));
//    private final Button cancel = new Button(By.xpath("//button[@data-qa='checkout_phone_form_modal_cancel_button']"));
//    private final Button save = new Button(By.xpath("//button[@data-qa='checkout_phone_form_modal_save_button']"));
//    private final Button close = new Button(By.xpath("//header/button"));// изменить, когда будет data-qa

    @Step("Заполнить номер телефона")
    public void fillPhoneNumber(String data) {
        phoneNumber.fill(data);
    }

    @Step("Очистить поле номер")
    public void clearPhoneNumber() {
        phoneNumber.clear();
    }
}
