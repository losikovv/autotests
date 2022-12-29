package ru.instamart.reforged.chatwoot.frame.contact_panel;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface ContactPanelElement {

    Element contactPanel = new Element(By.xpath("//div[contains(@class,'contact--panel')]"), "Панель информации о диалоге (справа от чата)");

    // Информация о контакте
    Button editContact = new Button(By.xpath("//button[contains(@class,'edit-contact')]"), "Кнопка 'Редактировать данные контакта'");
    Element contactName = new Element(By.xpath("//div[contains(@class,'contact--name')]"), "Имя контакта");
    Element contactEmail = new Element(By.xpath("(//div[@class='contact-info--details'])[1]"), "Email контакта");
    Element contactPhone = new Element(By.xpath("(//div[@class='contact-info--details'])[2]"), "Телефон контакта");
    Element contactCompany = new Element(By.xpath("(//div[@class='contact-info--details'])[3]"), "Компания контакта");
}
