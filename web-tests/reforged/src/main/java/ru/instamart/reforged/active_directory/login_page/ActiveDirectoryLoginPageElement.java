package ru.instamart.reforged.active_directory.login_page;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface ActiveDirectoryLoginPageElement {

    Input mailInput = new Input(By.id("userNameInput"),"Инпут ввода логина");
    Input passwordInput = new Input(By.id("passwordInput"),"Инпут ввода пароля");
    Element loginButton = new Element(By.xpath("//span[@id='submitButton']"),"Кнопка логина");
    Element wrongLoginOrPassAlert = new Element(By.xpath("//span[contains(text(),'Неверный идентификатор пользователя или пароль')]"),
            "Алерт неверного пароля или логина");
    Element wrongLoginFormatAlert = new Element(By.xpath("//span[contains(text(),'Введите идентификатор пользователя в формате')]"),
            "Алерт неверного пароля или логина");

}