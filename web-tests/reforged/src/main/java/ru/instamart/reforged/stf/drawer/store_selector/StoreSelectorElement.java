package ru.instamart.reforged.stf.drawer.store_selector;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;

public interface StoreSelectorElement {

    Element storeSelector = new Element(By.xpath("//div[@id='react-modal']//div[@data-qa= 'store-selector']"), "окно выбора магазинов");
    Link storeCard = new Link(By.xpath("//div[@id='react-modal']//a[@data-qa='store-card']"), "карточка магазина");
    Button editAddress = new Button(By.xpath("//button[@data-qa='editable_info_change_button']"), "кнопка Изменить адрес");
}