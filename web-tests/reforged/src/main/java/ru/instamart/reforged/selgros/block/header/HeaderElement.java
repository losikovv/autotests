package ru.instamart.reforged.selgros.block.header;

import org.openqa.selenium.By;
import ru.instamart.kraken.data.TestVariables;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.Link;

public interface HeaderElement {
    Element header = new Element(By.xpath("//header"), "Контейнер для хедера");

    Button delivery = new Button(By.xpath("//button[@data-qa='ship_selector_type_delivery']"), "Кнопка переключения способа заказа - 'Доставка'");
    Button pickup = new Button(By.xpath("//button[@data-qa='ship_selector_type_pickup']"), "Кнопка переключения способа заказа - 'Самовывоз'");
    Button selectAddressText = new Button(By.xpath("//button[@data-qa='shipping_method_button']"), "Кнопка выбора адреса с текстом 'Выберите адрес доставки'");
    Button selectAddress = new Button(By.xpath("//button[@data-qa='select-button']"), "Кнопка выбора адреса доставки");
    Element hotlineWorkHoursText = new Element(By.xpath("//header//span[text()='" + TestVariables.CompanyParams.companyHotlineWorkhoursShort + "']"), "Текст со временем работы горячей линии");
    Element hotlinePhoneNumber = new Element(By.xpath("//header//span[text()='" + TestVariables.CompanyParams.companyHotlinePhoneNumber + "']"), "Текст с номером телефона горячей линии");
    Link logo = new Link(By.xpath("//header//a[@href='/']"), "Лого магазина с редиректом на главную");

    Link help = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Помощь')]"), "Кнопка открытия раздела помощи");

    Button categoryMenu = new Button(By.xpath("//button[@data-qa='catalog-button']"), "Кнопка открытия меню категорий");
    Input searchInput = new Input(By.xpath("//input[@data-qa='suggester_header_form_input']"), "Инпут поиска товара");
    Button searchButton = new Button(By.xpath("//button[@data-qa='suggester_header_form_search_button']"), "Кнопка поиска в инпуте товара");

    Button cart = new Button(By.xpath("//button[@data-qa='open-cart-button']"), "Кнопка корзины");
    Button login = new Button(By.xpath("//button[@data-qa='login-button']"), "Кнопка логина");

    Element partnershipLabel = new Element(By.xpath("//div[@class='partnership']"), "Лейбл 'О сотрудничестве'");
    Element nearestDeliveryLabel = new Element(By.xpath("//span[text()='Ближайшая доставка']"), "Лейбл 'Ближайшая доставка (день) чч:мм-чч:мм'");
}
