package ru.instamart.reforged.chatwoot.block.menu_vertical_secondary;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface SecondaryMenuElement {

    Button secondaryMenuLinkByName = new Button(ByKraken.xpathExpression("//a[contains(@class,'secondary-menu--title')][contains(.,'%s')]"), "Пункт вертикального меню по названию");
    Element secondaryMenuActiveLink = new Element(By.xpath("//a[contains(@class,'secondary-menu--title')][contains(@class,'router-link-active')]"), "Активный пункт в вертикальном меню");
    ElementCollection sources = new ElementCollection(By.xpath("//li[.//span[contains(@class,'secondary-menu--title')][contains(.,'Источники')]]//li"), "Элементы группы 'Источники'");
    Element activeSource = new Element(By.xpath("//li[.//span[contains(@class,'secondary-menu--title')][contains(.,'Категории')]]//li[@class='active']"), "Выбранный пункт группы 'Источники'");
    ElementCollection categories = new ElementCollection(By.xpath("//li[.//span[contains(@class,'secondary-menu--title')][contains(.,'Категории')]]//li"), "Элементы группы 'Категории'");
    Element activeCategory = new Element(By.xpath("//li[.//span[contains(@class,'secondary-menu--title')][contains(.,'Категории')]]//li[@class='active']"), "Выбранный пункт группы 'Категории'");
    Element dialogsBoardTitle = new Element(By.xpath("//div[@class='chat-list__top']/h1[@title='Диалоги']"), "Заголовок вкладки 'Диалоги'");
    ElementCollection chatTabs = new ElementCollection(By.xpath("//ul[@class='tab--chat-type tabs']/li"), "Вкладки чатов");
    Element activeChatTab = new Element(By.xpath("//ul[@class='tab--chat-type tabs']/li[contains(@class,'is-active')]"), "Активная вкладка чатов");

}
