package ru.instamart.reforged.business.drawer.cart;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface CartElement {

    Element cartContainer = new Element(By.xpath("//div[@data-qa='cart']"), "Окно Корзина");
    Button closeButton = new Button(By.xpath("//button[@data-qa='cart_close-button']"), "Кнопка Закрыть");
    Element cartIsEmptyPlaceholder = new Element(By.xpath("//div[@class='new-cart-empty']"), "Плейсхолдер пустой корзины");
}
