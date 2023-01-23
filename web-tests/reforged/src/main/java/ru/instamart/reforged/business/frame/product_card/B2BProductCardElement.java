package ru.instamart.reforged.business.frame.product_card;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface B2BProductCardElement {

    Button close = new Button(By.xpath("//button[@data-qa='product_cards_close_button']"), "Кнопка 'Закрыть'");
    Element itemName = new Element(By.xpath("//div[@itemtype='http://schema.org/Product']"), "название продукта");
    Element favorite = new Element(By.xpath("//div[@data-qa='addToCart_favorite']"), "кнопка добавления в избранное");
    Button buy = new Button(By.xpath("//button[@data-qa='addToCart_buy_button']"), "добавить продукт в корзину");
    Button decrease = new Button(By.xpath("//button[@data-qa='addToCart_minus']"), "уменьшить количество продуктов в корзине");
    Button increase = new Button(By.xpath("//button[@data-qa='addToCart_plus']"), "увеличить количество продуктов в корзине");
}
