package ru.instamart.reforged.sber_id_auth.auth_page;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;

public interface SberIdAuthElement {

    Input phoneNumber = new Input(By.xpath("//input[@name='phoneNumber']"), "Поле ввода телефонного номера");
    Input login = new Input(By.xpath("//input[@name='login']"), "Поле ввода логина");
    Input password = new Input(By.xpath("//input[@name='password']"), "Поле ввода пароля");
    Button changeAuthTypeOnLogin = new Button(By.xpath("//span[text() = 'Войти по логину']/parent::button"), "Кнопка смены типа авторизации на логин");
    Button changeAuthTypeOnPhone = new Button(By.xpath("//span[text() = 'Войти по номеру телефона']/parent::button"), "Кнопка смены типа авторизации на номер телефона");
    Button submitLogin = new Button(By.xpath("//button[@type='submit']"), "Кнопка подтверждения ввода логина");
    Button submitPassword = new Button(By.xpath("//button[@type='submit']"), "Кнопка подтверждания ввода телефона");
    Button submitSmsCode = new Button(By.xpath("//button[@type='submit']"), "Кнопка подтвержденя ввода кода из смс");
    Button receivedSms = new Button(By.xpath("//span[text() = 'Я получил СМС-код']/parent::button"), "Кнопка подтверждения получения смс");
    Input smsCode = new Input(By.xpath("//input[@name='code']"), "Поле ввода кода из смс");

}
