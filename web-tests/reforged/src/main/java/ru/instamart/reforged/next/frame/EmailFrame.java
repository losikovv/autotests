package ru.instamart.reforged.next.frame;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;

public final class EmailFrame implements Close {

    private final Input email = new Input(By.xpath("//input[@data-qa='change_email_form_email_input']"), "поле для ввода Email");
    private final Button submit = new Button(By.xpath("//button[@data-qa='change_email_form_submit_button']"), "подтвердить форму");

    private final Button ok = new Button(By.xpath("//button[@data-qa='change_email_success_button']"), "кнопка 'Хорошо'");

    @Step("Ввести почту {0} в поле 'Email'")
    public void fillEmail(final String data) {
        email.fill(data);
    }

    @Step("Подтвердить форму")
    public void submit() {
        submit.click();
    }

    @Step("Нажать на кнопку 'Хорошо'")
    public void clickToOk() {
        ok.click();
    }
}
