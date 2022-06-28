package ru.instamart.reforged.business.drawer.cart;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Input;

public interface B2BCartElement {

    Element cartContainer = new Element(By.xpath("//div[@data-qa='cart']"), "Окно Корзина");
    Button closeButton = new Button(By.xpath("//button[@data-qa='cart_close-button']"), "Кнопка Закрыть");
    Element cartIsEmptyPlaceholder = new Element(By.xpath("//div[@class='new-cart-empty']"), "Плейсхолдер пустой корзины");
    Element itemCounter = new Element(By.xpath("//div[@data-qa='line-item-counter']//div"), "Отображаемое количество первого товара в корзине");
    Input itemCounterInput = new Input(By.xpath("//div[@data-qa='line-item-counter']//input"), "Поле ввода количества первого товара в корзине");

    Element totalVat = new Element(By.xpath("//div[@class='cart-checkout__total-vat-container']"), "Блок 'НДС к возврату'");
    ElementCollection retailers = new ElementCollection(By.xpath("//div[@class='cart-retailer']"), "Ритейлеры в корзине");
    ElementCollection items = new ElementCollection(By.xpath("//div[@data-qa='line-item']"), "Товары в корзине");
    Element firstItem = new Element(By.xpath("//div[@data-qa='line-item']"), "Первый товар в корзине");

    Button submitCheckout = new Button(By.xpath("//button[@data-qa='cart_checkout_button']"), "Кнопка 'Сделать заказ'");
}
