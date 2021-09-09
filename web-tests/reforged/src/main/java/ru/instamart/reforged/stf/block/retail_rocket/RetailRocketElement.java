package ru.instamart.reforged.stf.block.retail_rocket;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface RetailRocketElement {

    Element carousel = new Element(By.id("Carousel1"),"карусель виджета");
    ElementCollection items = new ElementCollection(By.xpath("//div[@id='Carousel1']//a"), "все элементы в карусели");
    ElementCollection addItemButtons = new ElementCollection(By.xpath("//div[@id='Carousel1']//button[@title='Добавить в корзину']"),
            "все кнопки 'Добавить в корзину' в карусели");
}
