package ru.instamart.reforged.business.block.header;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface HeaderElement {

    Element header = new Element(By.xpath("//header"), "Контейнер для хедера");

    Button login = new Button(By.xpath("//button[contains(.,'Войти')]"), "Кнопка 'Войти'");
    Button profile = new Button(By.xpath("//button[@data-qa='profile-button_button']"), "Кнопка профиль пользователя в хэдере");

}
