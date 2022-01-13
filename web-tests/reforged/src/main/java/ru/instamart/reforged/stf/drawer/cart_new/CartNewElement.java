package ru.instamart.reforged.stf.drawer.cart_new;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.stf.frame.ClearCartModal;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;

public interface CartNewElement {

    ClearCartModal clearCartModal = new ClearCartModal();
    ProductCard productCard = new ProductCard();

    Element cartContainer = new Element(By.xpath("//div[@data-qa='cart']"), "empty"); //Для контекстного поиска, например, чтобы однозначно определить блок популярных товаров
    Element title = new Element(By.xpath("//div[@class='new-cart__title']"), "заголовок Корзины");
    Button closeButton = new Button(By.xpath("//button[@data-qa='cart_close-button']"), "кнопка Закрыть");
    Element cartIsEmptyPlaceholder = new Element(By.xpath("//div[@class='new-cart-empty']"), "плейсхолдер пустой корзины");
    Button backToCatalogButton = new Button(By.xpath("//button[@data-qa='cart_return_to_catalog_button']"), "кнопка Вернуться в каталог");
    Button confirmOrderButton = new Button(By.xpath("//button[@data-qa='cart_checkout_button']"), "кнопка Сделать заказ");
    Element orderAmount = new Element(By.xpath("//div[@class='cart-checkout-link__well']"), "Лейбл суммы заказа");

    ElementCollection retailers = new ElementCollection(By.xpath("//div[@class='cart-retailer']"), "empty");
}
