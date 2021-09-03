package ru.instamart.reforged.stf.page.user.profile;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface UserProfileElement {
    Button openFavorite = new Button(By.xpath("//div[text()='Любимые товары']"),
            "кнопка любимые товары в профиде пользователя");

    Button openOrders = new Button(By.xpath("//div[text()='Заказы']"),
            "кнопка любимые товары в профиде пользователя");

    Button openAccount = new Button(By.xpath("//div[text()='Аккаунт']"),
            "кнопка любимые товары в профиде пользователя");

    Element textNoOrders = new Element(By.xpath("//h3[text()='У вас нет завершенных заказов']"),
            "текст сообщающий, что у нового пользователя нет заказов");

    Button activeOrders = new Button(By.xpath("//button[@data-qa='user-shipment-list-selector-active']"),
            "кнопка активные заказы");

    Button finishedOrders = new Button(By.xpath("//button[@data-qa='user-shipment-list-selector-inactive']"),
            "кнопка завершенные заказы");

    Button allOrders = new Button(By.xpath("//button[@data-qa='user-shipment-list-selector-all']"),
            "кнопка все заказы");

    Button goToShopping = new Button(By.xpath("//a[@data-qa='user-shipment-list-buy-more']"),
            "кнопка перейти к покупкам");
}
