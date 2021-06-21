package ru.instamart.reforged.stf.frame.auth;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.page.Window;

public final class AuthFacebook implements Window {

    private final Input email = new Input(By.xpath("//input[@id='email']"));
    private final Input password = new Input(By.xpath("//input[@id='pass']"));
    private final Element login = new Element(By.xpath("//label[@id='loginbutton']"));

    @Step("Ввести email на странице авторизации через FB")
    public void setEmail(final String text) {
        email.fill(text);
    }

    @Step("Ввести пароль на странице авторизации через FB")
    public void setPassword(final String text) {
        password.fill(text);
    }

    @Step("Нажать Вход на странице авторизации через FB")
    public void clickToLogin() {
        login.click();
    }
}
