package ru.instamart.reforged.stf.block.retail_rocket;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface RetailRocketElement {

    Element carouselPopular = new Element(By.id("Carousel0"), "карусель на главной");
    ElementCollection itemsPopular = new ElementCollection(By.xpath("//div[@id='Carousel0']//a"), "все элементы в карусели 'Популярные товары'");
    ElementCollection addItemButtonsPopular = new ElementCollection(By.xpath("//div[@id='Carousel0']//button[@title='Добавить в корзину']"),
            "все кнопки 'Добавить в корзину' в карусели 'Популярные товары'");

    Element carousel = new Element(By.id("Carousel1"), "карусель виджета в корзине");
    ElementCollection items = new ElementCollection(By.xpath("//div[@id='Carousel1']//a"), "все элементы в карусели");
    ElementCollection addItemButtons = new ElementCollection(By.xpath("//div[@id='Carousel1']//button[@title='Добавить в корзину']"),
            "все кнопки 'Добавить в корзину' в карусели");

    Element carouselSimilar = new Element(By.id("Carousel2"), "карусель виджета в карточке товара");
    ElementCollection itemsSimilar = new ElementCollection(By.xpath("//div[@id='Carousel2']//a"), "все элементы в карусели 'Похожие'");
    ElementCollection addItemButtonsSimilar = new ElementCollection(By.xpath("//div[@id='Carousel2']//button[@title='Добавить в корзину']"),
            "все кнопки 'Добавить в корзину' в карусели");
}
