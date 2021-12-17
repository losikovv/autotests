package ru.instamart.reforged.stf.frame.auth;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.Selector;
import ru.instamart.reforged.core.page.Window;

public final class AuthMail implements Window {

    private final Input name = new Input(By.xpath("//input[@name='username']"), "поле для ввода имени");
    private final Button enterPassword = new Button(By.xpath("//button[@data-test-id='next-button']"), "подтверждение ввода почты");

    private final Input password = new Input(By.xpath("//input[@name='password']"), "поле для ввода пароля");
    private final Button login = new Button(By.xpath("//button[@data-test-id='submit-button']"), "подтвердить авторизацию");
    private final Button accept = new Button(By.xpath("//button[@data-test-id='accept-access']"), "разрешить авторизацию");

    @Step("Ввести email на странице авторизации через mail.ru")
    public void fillName(final String text) {
        name.fill(text);
    }

    @Step("Нажать Ввести пароль на странице авторизации через mail.ru")
    public void clickToEnterPassword() {
        enterPassword.click();
    }

    @Step("Ввести пароль на странице авторизации через mail.ru")
    public void fillPassword(final String text) {
        password.fill(text);
    }

    @Step("Нажать войти на странице авторизации через mail.ru")
    public void clickToSubmit() {
        login.click();
    }

    @Step("Разрешить авторизоваться на сайте")
    public void clickToAccept() {
        accept.click();
    }
}
