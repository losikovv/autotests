package ru.instamart.reforged.stf.drawer.category_menu;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface CategoryMenuElement {
    Button close = new Button(By.xpath("//div[@data-qa='category-menu']//button[@data-qa='close-button']"), "Кнопка закрытия шторки каталога");
    ElementCollection category = new ElementCollection(By.xpath("//div[@data-qa='category-menu']//div[@class='category-menu-item__title']"), "Коллекция элементов меню категорий");

    Element categoryMenuDrawer = new Element(By.xpath("//nav[@data-qa='category-menu']"), "Шторка каталога меню");
}
