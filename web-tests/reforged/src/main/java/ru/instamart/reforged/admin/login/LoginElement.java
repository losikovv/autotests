package ru.instamart.reforged.admin.login;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface LoginElement {

    Input username = new Input(By.name("email"));
    Input password = new Input(By.name("password"));
    Button submit = new Button(By.xpath("//button[@type='submit']"));

    Element title = new Element(By.xpath("//h1[text()='Вход']"));
    Element errorSetEmail = new Element(By.xpath("//div[@role='alert' and text()='Укажите email']"));
    Element errorInvalidFormatEmail = new Element(By.xpath("//div[@role='alert' and text()='Email адрес имеет неправильный формат']"));
    Element errorSetPassword = new Element(By.xpath("//div[@role='alert' and text()='Укажите пароль']"));
    Element errorShortPassword = new Element(By.xpath("//div[@role='alert' and text()='Пароль должен содержать не менее 6 символов']"));
    Element errorInvalidEmailOrPassword = new Element(By.xpath("//div[@role='alert' and text()='Неверный email или пароль']"));
}
