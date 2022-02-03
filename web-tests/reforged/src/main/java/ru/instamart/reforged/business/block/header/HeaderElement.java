package ru.instamart.reforged.business.block.header;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface HeaderElement {

    Element header = new Element(By.xpath("//header"), "Контейнер для хедера");

    Button selectAddress = new Button(By.xpath("//button[@data-qa='select-button']"), "Кнопка 'Указать адрес'");
    Element enteredAddress = new Element(By.xpath("//span[@data-qa='current-ship-address']"), "Лэйбл отображающий введенный адрес в шапке");
    Button login = new Button(By.xpath("//button[contains(.,'Войти')]"), "Кнопка 'Войти'");
    Button profile = new Button(By.xpath("//button[@data-qa='profile-button_button']"), "Кнопка профиль пользователя в хэдере");
    Element cartNotification = new Element(By.xpath("//div[@class='notification']"), "Алерт добавления товара в корзину");
    Button cart = new Button(By.xpath("//button[@data-qa='open-cart-button']"), "Кнопка 'Корзина'");
    Element itemsCountSpoilerOnCartButton = new Element(By.xpath("//button[@data-qa='open-cart-button']/following-sibling::span"), "Значок о количестве товаров в корзине над кнопкой");
}
