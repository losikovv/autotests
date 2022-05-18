package ru.instamart.reforged.next.page.seo_incognito;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.next.block.header.Header;


public interface SeoIncognitoElement {

    Header header = new Header();

    Element productsGrid = new Element(By.xpath("//div[@class='category-products-wrap__products']"), "сетка товаров  на странице");
    Element firstProduct = new Element(By.xpath("(//a[@itemprop='itemListElement'])[1]"), "первый товар на странице");
    Button addFirstProductToCart = new Button(By.xpath("(//button[contains(@class,'cart-actions__btn--visible')])[1]"), "кнопка добавить в корзину первого товара");

}
