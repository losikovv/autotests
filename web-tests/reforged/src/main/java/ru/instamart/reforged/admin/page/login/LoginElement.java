package ru.instamart.reforged.admin.page.login;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface LoginElement {

    Input username = new Input(By.xpath("//input[@data-qa='login_form_email_input']"), "поле для ввода email");
    Input password = new Input(By.xpath("//input[@data-qa='login_form_password_input']") ,"поле для ввода пароля");
    Button submit = new Button(By.xpath("//button[@data-qa='login_form_submit']"), "кнопка авторизации");

    Element title = new Element(By.xpath("//h1[@data-qa='login_form_title']"), "заголовок на странице авторизации");
    Element errorSetEmail = new Element(By.xpath("//span[@data-qa='login_form_email_input_alert_message_error' and text()='Укажите email']"),
            "ошибка авторизации 'Укажите email'");
    Element errorInvalidFormatEmail = new Element(By.xpath("//span[@data-qa='login_form_email_input_alert_message_error' and text()='Email адрес имеет неправильный формат']"),
            "ошибка авторизации 'Email адрес имеет неправильный формат'");
    Element errorSetPassword = new Element(By.xpath("//span[@data-qa='login_form_password_input_alert_message_error' and text()='Укажите пароль']"),
            "ошибка авторизации 'Укажите пароль'");
    Element errorShortPassword = new Element(By.xpath("//span[@data-qa='login_form_password_input_alert_message_error' and text()='Пароль должен содержать не менее 6 символов']"),
            "ошибка авторизации 'Пароль должен содержать не менее 6 символов'");
    Element errorInvalidEmailOrPassword = new Element(By.xpath("//span[@data-qa='login_form_email_input_alert_message_error' and text()='Неверный email или пароль']"),
            "ошибка авторизации 'Неверный email или пароль'");
    Element errorNoPermission = new Element(By.xpath("//div[@class = 'flash error' and text()='У вас нет прав для этого действия. Если они вам необходимы, обратитесь за помощью в Helpdesk.']"),
            "нотификация с ошибкой 'У вас нет прав для этого действия'");
}
