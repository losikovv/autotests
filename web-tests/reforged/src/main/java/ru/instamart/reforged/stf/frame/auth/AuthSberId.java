package ru.instamart.reforged.stf.frame.auth;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;

public final class AuthSberId {

    private final Input phoneNumber = new Input(By.xpath("//input[@name='phone-number']"));
    private final Input login = new Input(By.xpath("//input[@name='login']"));
    private final Input password = new Input(By.xpath("//input[@name='password']"));
    private final Button changeAuthTypeOnLogin = new Button(By.xpath("//span[text() = 'Войти по логину']/parent::button"));
    private final Button changeAuthTypeOnPhone = new Button(By.xpath("//span[text() = 'Войти по номеру телефона']/parent::button"));
    private final Button submitLogin = new Button(By.xpath("//button[@type='submit']"));
    private final Button submitPassword = new Button(By.xpath("//button[@type='submit']"));
    private final Button submitSmsCode = new Button(By.xpath("//button[@type='submit']"), "кнопка подтверждения введенного кода из смс");
    private final Button receivedSms = new Button(By.xpath("//span[text() = 'Я получил СМС-код']/parent::button"));
    private final Input smsCode = new Input(By.xpath("//input[@name='code']"), "поле ввода кода из смс");

    @Step("Ввести номер телефона на странице авторизации через sberID")
    public void fillPhoneNumber(String data) {
        phoneNumber.fill(data);
    }

    @Step("Ввести логин на странице авторизации через sberID")
    public void fillLogin(String data) {
        login.fill(data);
    }

    @Step("Ввести пароль на странице авторизации через sberID")
    public void fillPassword(String data) {
        password.fill(data);
    }

    @Step("Нажать войти по логину на странцие авторизации через sberId")
    public void clickToChangeAuthTypeOnLogin() {
        changeAuthTypeOnLogin.click();
    }

    @Step("Нажать подтвердить логин")
    public void clickToSubmitLogin() {
        submitLogin.click();
    }

    @Step("Нажать подтвердить пароль")
    public void clickToSubmitPassword() {
        submitPassword.click();
    }

    @Step("Нажать 'Я получил смс'")
    public void clickToReceivedSms() {
        receivedSms.click();
    }

    @Step("Ввести смс - код:{0}")
    public void enterCode(String code) {
        smsCode.fill(code);
    }

    @Step("Нажать подтвердить смс")
    public void clickToSubmitSmsCode() {
        submitSmsCode.click();
    }

}
