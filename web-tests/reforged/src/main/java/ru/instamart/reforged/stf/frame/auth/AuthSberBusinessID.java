package ru.instamart.reforged.stf.frame.auth;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;

public final class AuthSberBusinessID {

    private final Input login = new Input(By.xpath("//input[@data-test-id='login--input']"));
    private final Input password = new Input(By.xpath("//input[@data-test-id='password--input']"));
    private final Input smsCodeCell = new Input(By.xpath("//input[@data-test-id='smsCode--input']"));

    private final Button nextButton = new Button(By.xpath("//button[@data-test-id='login-submit--button']"));

    @Step("Ввести email на странице авторизации через СберБизнесID")
    public void setLogin(final String text) {
        login.fill(text);
    }

    @Step("Ввести пароль на странице авторизации через СберБизнесID")
    public void setPassword(final String text) {
        password.fill(text);
    }

    @Step("Нажать Далее на странице авторизации через СберБизнесID")
    public void clickToNext() {
        nextButton.click();
    }

    @Step("Заполнить смс-код на странице авторизации через СберБизнесID")
    public void enterCode(final String code) {
        smsCodeCell.fill(code);
    }

}
