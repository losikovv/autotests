package ru.instamart.reforged.stf.frame.auth;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.Selector;

public class AuthMail {

    private final Input name = new Input(By.xpath("//input[@name='username']"));
    private final Selector domain = new Selector(By.xpath("//select[@name='Domain']"));
    private final Button enterPassword = new Button(By.xpath("//button[@data-test-id='next-button']"));
    private final Button viaVk = new Button(By.xpath("//button[@data-test-id='social-vk']"));
    private final Button cancel = new Button(By.xpath("//span[contains(text(), 'Отмена')]/parent::button"));

    private final Input password = new Input(By.xpath("//input[@name='password']"));
    private final Element togglePassword = new Element(By.xpath("//div[@data-test-id='toggle-password-mask']"));
    private final Button login = new Button(By.xpath("//button[@data-test-id='submit-button']"));

    @Step("Ввести email на странице авторизации через mail.ru")
    public void setName(final String text) {
        name.fill(text);
    }

    @Step("Нажать Ввести пароль на странице авторизации через mail.ru")
    public void clickToEnterPassword() {
        enterPassword.click();
    }

    @Step("Выбрать домен на странице авторизации через mail.ru")
    public void setDomain(final String text) {
        domain.selectByText(text);
    }

    @Step("Ввести пароль на странице авторизации через mail.ru")
    public void setPassword(final String text) {
        password.fill(text);
    }

    @Step("Нажать VK Connect на странице авторизации через mail.ru")
    public void clickToVkConnect() {
        viaVk.click();
    }

    @Step("Нажать войти на странице авторизации через mail.ru")
    public void clickToSubmit() {
        login.click();
    }

    @Step("Нажать отмена на странице авторизации через mail.ru")
    public void clickToCancel() {
        cancel.click();
    }

    @Step("Нажать скрыть/показать пароль на странице авторизации через mail.ru")
    public void clickToToggle() {
        togglePassword.click();
    }
}
