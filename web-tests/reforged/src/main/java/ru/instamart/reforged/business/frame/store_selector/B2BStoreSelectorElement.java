package ru.instamart.reforged.business.frame.store_selector;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;

public interface B2BStoreSelectorElement {

    Element storeSelector = new Element(By.xpath("//div[@data-qa= 'store-selector']"), "окно выбора магазинов");
    Link firstStoreCard = new Link(By.xpath("(//a[@data-qa='store-card'])[1]"), "карточка первого магазина");
    Element storeCardBySid = new Element(ByKraken.xpathExpression("//a[@data-qa='store-card'][@href='/stores/%s']"), "Карточка магазина по SID");
    Button editAddress = new Button(By.xpath("//button[@data-qa='editable_info_change_button']"), "кнопка Изменить адрес");
}