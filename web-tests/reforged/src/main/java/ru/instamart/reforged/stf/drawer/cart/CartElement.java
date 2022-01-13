package ru.instamart.reforged.stf.drawer.cart;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.stf.block.retail_rocket.RetailRocket;

public interface CartElement {
    RetailRocket retailRocket = new RetailRocket();

    Element cartContainer = new Element(By.xpath("//div[@data-qa='cart']"), "Окно Корзина");
    Element title = new Element(By.xpath("//div[@class='new-cart__title']"), "Заголовок окна Корзина");
    Button closeButton = new Button(By.xpath("//button[@data-qa='cart_close-button']"), "Кнопка Закрыть");
    Element cartIsEmptyPlaceholder = new Element(By.xpath("//div[@class='new-cart-empty']"), "Плейсхолдер пустой корзины");
    Button backToCatalogButton = new Button(By.xpath("//button[@data-qa='cart_return_to_catalog_button']"), "Кнопка Вернуться в каталог");
    Button submitOrder = new Button(By.xpath("//button[@data-qa='cart_checkout_button']"), "Кнопка Сделать заказ");
    Element orderAmount = new Element(By.xpath("//div[@class='cart-checkout-link__well']"), "Лейбл суммы заказа");
    Element retailRocketBlock = new Element(By.xpath("//div[@data-qa='cart']//div[contains(@class, 'retail-rocket-block')]"), "Блок Не забудьте купить");

    Element firstItem = new Element(By.xpath("//div[@data-qa='line-item']"), "Первый товар в корзине");
    ElementCollection items = new ElementCollection(By.xpath("//div[@data-qa='line-item']"), "Все товары в корзине");
    Element firstRetailer = new Element(By.xpath("//div[@class='cart-retailer']"), "Первый магазин в корзине");
    ElementCollection retailers = new ElementCollection(By.xpath("//div[@class='cart-retailer']"), "Все магазины в корзине");
}
