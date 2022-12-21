package ru.instamart.reforged.chatwoot.page.login;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.Link;

public interface LoginElement {
    Element chatwootLogo = new Element(By.xpath("//img[@alt='Chatwoot']"), "Логотип Chatwoot");
    Element loginTitle = new Element(By.xpath("//h2[@class='hero__title'][contains(.,'Войти в Chatwoot')]"), "Заголовок формы логина");
    Input emailInput = new Input(By.xpath("//input[@data-testid='email_input']"), "Поле ввода 'email'");
    Element emailError = new Element(By.xpath("//input[@data-testid='email_input']/parent::label[@class='error']"), "Алерт ошибки в поле ввода 'email'");
    Input passwordInput = new Input(By.xpath("//input[@data-testid='password_input']"), "Поле ввода 'пароль'");
    Button submit = new Button(By.xpath("//button[@data-testid='submit_button']"), "Кнопка 'Вход'");
    Link resetPassword = new Link(By.xpath("//a[contains(.,'Забыли пароль?')]"), "Ссылка 'Забыли пароль?'");
    Link gotoRegistration = new Link(By.xpath("//a[contains(.,'Создать новый аккаунт')]"), "Ссылка 'Создать новый аккаунт'");
}
