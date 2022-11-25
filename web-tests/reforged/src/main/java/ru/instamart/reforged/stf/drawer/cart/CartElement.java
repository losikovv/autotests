package ru.instamart.reforged.stf.drawer.cart;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.stf.block.retail_rocket.RetailRocket;
import ru.instamart.reforged.stf.frame.ClearCartModal;

public interface CartElement {
    RetailRocket retailRocket = new RetailRocket();
    ClearCartModal clearCartModal = new ClearCartModal();

    Element cartContainer = new Element(By.xpath("//div[@data-qa='cart']"), "Окно Корзина");
    Element title = new Element(By.xpath("//div[@class='new-cart__title']"), "Заголовок окна Корзина");
    Button closeButton = new Button(By.xpath("//button[@data-qa='cart_close-button']"), "Кнопка Закрыть");
    Element cartIsEmptyPlaceholder = new Element(By.xpath("//div[@class='new-cart-empty']"), "Плейсхолдер пустой корзины");
    Button backToCatalogButton = new Button(By.xpath("//button[@data-qa='cart_return_to_catalog_button']"), "Кнопка Вернуться в каталог");
    Button submitOrder = new Button(By.xpath("//div[@class='cart-checkout']//button[@type='submit']"), "Кнопка Сделать заказ");
    Element orderAmount = new Element(By.xpath("//div[@class='cart-checkout-link__well']"), "Лейбл суммы заказа");
    Element retailRocketBlock = new Element(By.xpath("//div[@data-qa='cart']//div[contains(@class, 'retail-rocket-block')]"), "Блок Не забудьте купить");

    Element firstItem = new Element(By.xpath("//div[@data-qa='line-item']"), 20, "Первый товар в корзине");
    ElementCollection items = new ElementCollection(By.xpath("//div[@data-qa='line-item']"), "Все товары в корзине");
    Element firstRetailer = new Element(By.xpath("//div[@class='cart-retailer']"), "Первый магазин в корзине");
    Element retailerByName = new Element(ByKraken.xpathExpression("//div[@class='cart-retailer__header']/div/a/img[@alt='%s']"), "Ритейлер по названию");
    ElementCollection retailers = new ElementCollection(By.xpath("//div[@class='cart-retailer']"), "Все магазины в корзине");
    Element viewOrder = new Element(By.xpath("//a[@data-qa='merged_products_look_button']"), "Кнопка 'посмотреть' заказ после мерджа товаров");

    Element positionsCount = new Element(By.xpath("//div[@class='cart-retailer-details__well']"), "Количество позиций корзины");
}
