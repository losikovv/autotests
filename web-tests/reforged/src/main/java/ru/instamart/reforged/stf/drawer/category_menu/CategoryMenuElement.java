package ru.instamart.reforged.stf.drawer.category_menu;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface CategoryMenuElement {
    Button close = new Button(By.xpath("//nav[@data-qa='category-menu']//button[@data-qa='close-button']"), "Кнопка закрытия шторки каталога");
    ElementCollection firstLevelCategory = new ElementCollection(By.xpath("//nav/ul[contains(@class, 'CategoriesMenuList')]/li/a"), "Коллекция элементов меню категорий первого уровня");
    ElementCollection secondLevelCategory = new ElementCollection(By.xpath("//nav/ul[contains(@class, 'CategoriesMenuList')]/li/ul/li/a"), "Коллекция элементов меню категорий второго уровня");

    Element categoryMenuDrawer = new Element(By.xpath("//nav[@data-qa='category-menu']"), "Шторка каталога меню");
}
