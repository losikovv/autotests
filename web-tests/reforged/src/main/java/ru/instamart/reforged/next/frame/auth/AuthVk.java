package ru.instamart.reforged.next.frame.auth;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.page.Window;

public final class AuthVk implements Window {

    private final Input email = new Input(By.xpath("//input[@name='email']"), "поле для ввода email");
    private final Input password = new Input(By.xpath("//input[@name='pass']"), "поле для ввода пароля");
    private final Button login = new Button(By.xpath("//button[@type='submit']"), "кнопка сабмита формы");

    @Step("Ввести email на странице авторизации через VK")
    public void setEmail(final String text) {
        email.fill(text);
    }

    @Step("Ввести пароль на странице авторизации через VK")
    public void setPassword(final String text) {
        password.fill(text);
    }

    @Step("Нажать Вход на странице авторизации через VK")
    public void clickToLogin() {
        login.click();
    }
}
