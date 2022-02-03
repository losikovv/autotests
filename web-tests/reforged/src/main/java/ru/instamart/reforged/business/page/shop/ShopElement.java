package ru.instamart.reforged.business.page.shop;

import org.openqa.selenium.By;
import ru.instamart.reforged.business.block.header.Header;
import ru.instamart.reforged.business.drawer.cart.Cart;
import ru.instamart.reforged.business.frame.address.Address;
import ru.instamart.reforged.business.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.business.frame.product_card.ProductCard;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface ShopElement {

    Header header = new Header();
    ProductCard productCard = new ProductCard();
    Cart cart = new Cart();
    AuthModal authModal = new AuthModal();
    Address address = new Address();

    Element spinner = new Element(By.xpath("//div[@data-qa='catalog_page_popular_products']/div"), "Спиннер блока рекомендаций");

    //Сниппет продукта и его элементы
    Element firstProductSnippet = new Element(By.xpath("//div[contains(@data-qa,'_product_item_')]"), "Спиппет первого товара на странице");
    Button addFirstItemToFavorite = new Button(By.xpath("//div[contains(@data-qa,'_product_item_')]/button[@title='Добавить в избранное']"), "Кнопка 'Добавить в избранное' у первого товара");
    Button plusFirstItemToCart = new Button(By.xpath("//div[contains(@data-qa,'_product_item_')]//button[@title='Добавить в корзину']"), "Кнопка добавить в корзину у первого товара");
    Button minusFirstItemFromCart = new Button(By.xpath("//div[contains(@data-qa,'_product_item_')]//button[contains(@title, 'Убрать из корзины')]"), "Кнопка убрать из корзины у первого товара");
    Element imageInFirstItem = new Element(By.xpath("//div[contains(@data-qa,'_product_item_')]//img"), "Изображение первого товара");
    Element nameInFirstItem = new Element(By.xpath("//div[contains(@data-qa,'_product_item_')]//h3"), "Название товара");
    Element packageSizeInFirstItem = new Element(By.xpath("//div[contains(@data-qa,'_product_item_')]//h3/following-sibling::div[1]"), "Размер упаковки товара");
    ElementCollection pricesInItemWithoutDiscount = new ElementCollection(By.xpath("(//div[contains(@data-qa,'_product_item_')][not(.//li[.//span[contains(.,'Скидка')]])])[1]//h3/following-sibling::div[2]/div"), "Отображаемые цены (для первого товара без скидки)");
    ElementCollection pricesInItemWithDiscount = new ElementCollection(By.xpath("(//div[contains(@data-qa,'_product_item_')][.//li[.//span[contains(.,'Скидка')]]])[1]//h3/following-sibling::div[2]/div[./span]"), "Отображаемые цены (для первого товара со скидкой)");
    Element priceInFirstItemWithoutDiscount = new Element(By.xpath("//div[contains(@data-qa,'_product_item_')][not(.//li[.//span[contains(.,'Скидка')]])]//div[./span[contains(.,'Цена за')]]"), "Цена товара (для первого товара без скидки)");
    Element fullPriceInFirstItemWithDiscount = new Element(By.xpath("//div[contains(@data-qa,'_product_item_')][.//li[.//span[contains(.,'Скидка')]]]//h3/following-sibling::div[2]/div[contains(.,'Цена без скидки')]"), "Цена товара без скидки (для первого товара со скидкой)");
    Element discountPriceInFirstItemWithDiscount = new Element(By.xpath("//div[contains(@data-qa,'_product_item_')][.//li[.//span[contains(.,'Скидка')]]]//h3/following-sibling::div[2]/div[contains(.,'Цена со скидкой')]"), "Цена товара со скидкой (для первого товара со скидкой)");
}
