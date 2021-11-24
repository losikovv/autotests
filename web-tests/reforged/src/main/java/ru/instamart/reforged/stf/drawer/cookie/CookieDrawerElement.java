package ru.instamart.reforged.stf.drawer.cookie;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;

public interface CookieDrawerElement {

    Element alert = new Element(By.xpath("//div[contains(text(),'На СберМаркете есть cookies')]"), "алерт с информацией о cookie");
    Button alertButton = new Button(By.xpath("//span[text()='Понятно']"), "кнопка Понятно");
    Link alertLink = new Link(By.xpath("//a[@href='/cookie']"), "ссылка Как это работает");
}
