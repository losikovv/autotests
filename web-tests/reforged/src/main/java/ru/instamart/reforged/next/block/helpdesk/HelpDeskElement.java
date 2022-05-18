package ru.instamart.reforged.next.block.helpdesk;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface HelpDeskElement {

    Button chatButton = new Button(By.id("hde-chat-button"), "кнопка открытия контейнера с чатом");
    Button chatButtonClose = new Button(By.id("hde-chat-button-close"), "кнопка закрытия чата");
    Element chatContainer = new Element(By.xpath("//div[@class='hde-container-mobile']"), "окно чата HD");
}
