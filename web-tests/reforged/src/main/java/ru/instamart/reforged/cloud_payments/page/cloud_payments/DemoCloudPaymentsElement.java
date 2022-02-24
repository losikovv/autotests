package ru.instamart.reforged.cloud_payments.page.cloud_payments;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;

public interface DemoCloudPaymentsElement {

    Input captchaAnswerInput = new Input(By.xpath("//input[@id='password']"),"Инпут ввода ответа на вопрос");
    Button confirmPaymentButton = new Button(By.xpath("//button[@type='submit']"),"Кнопка подтверждения платежа");
}
