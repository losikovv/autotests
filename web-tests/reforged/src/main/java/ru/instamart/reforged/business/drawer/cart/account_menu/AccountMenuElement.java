package ru.instamart.reforged.business.drawer.cart.account_menu;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;

public interface AccountMenuElement {

    Element username = new Element(By.xpath("//div[@data-qa='account-menu-username']"), "Имя пользователя или телефон");

    Link profile = new Link(By.xpath("//a[@data-qa='account-menu-profile']"), "кнопка профиля пользователя в дропдауне пользователя");
    Link companies = new Link(By.xpath("//a[@data-qa='account-menu-companies']"), "ссылка компании");
    Link terms = new Link(By.xpath("//a[@data-qa='account-menu-terms']"), "ссылка условия пользования");

    Button logout = new Button(By.xpath("//button[@data-qa='account-menu-logout']"), "кнопка выхода из аккаунта");

    Button delivery = new Button(By.xpath("//button[@data-qa='account-menu-delivery']"), "ссылка 'Доставка'");
    Link faq = new Link(By.xpath("//a[@data-qa='account-menu-faq']"), "Ссылка 'FAQ'");

    Element accountMenu = new Element(By.xpath("//div[@data-qa='account-menu']"), "Меню пользователя");

}
