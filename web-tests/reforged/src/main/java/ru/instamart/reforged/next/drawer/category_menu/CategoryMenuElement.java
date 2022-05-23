package ru.instamart.reforged.next.drawer.category_menu;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface CategoryMenuElement {
    Button close = new Button(By.xpath("//nav[@data-qa='category-menu']//button[@data-qa='close-button']"),
            "Кнопка закрытия шторки каталога");
    ElementCollection firstLevelCategory = new ElementCollection(By.xpath("//ul[@data-track-id='categories-menu-list']/li[attribute::*[contains(local-name(), 'data-item-id')]]/a//h3"),
            "Коллекция элементов меню категорий первого уровня");
    ElementCollection secondLevelCategory = new ElementCollection(By.xpath("//ul[@data-track-id='categories-menu-list']//ul/li/a[@data-track-id='categories-menu-link']//h3"),
            "Коллекция элементов меню категорий второго уровня");
    Element categoryMenuDrawer = new Element(By.xpath("//nav[@data-qa='category-menu']"), "Шторка каталога меню");
}
